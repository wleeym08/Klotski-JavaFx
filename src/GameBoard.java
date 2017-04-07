/**
 * Program Name: GameBoard.java
 * Discussion:   GameBoard class for the game (extends Pane)
 * Written By:   Zhiying Li
 * Date:         2016/12/12
 */

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Stack;

class GameBoard extends Pane {
    // use a stack to store the info about every move
    // use a tree to store the info of all the possible moves
    // use a hashmap-like way to compare different patterns
    // to reduce the repetitive patterns

    final private Label counter;
    private int stepCount;
    final private Rectangle border;
    final private Rectangle[] block = new Rectangle[10];
    final private Stack<MoveInfo> infoStack = new Stack<>();
    final private Node<MoveInfo> infoTree = new Node<>();
    final private ArrayList<String> hashmap = new ArrayList<>();

    public GameBoard() {
        stepCount = 0;
        counter = new Label("Moves: \n"  + stepCount);
        counter.setFont(new Font("Comic Sans MS", 32));

        // initialize blocks

        border = new Rectangle(95, 95, 410, 510);
        block[0] = new Rectangle(200, 100, 200, 200);
        block[1] = new Rectangle(100, 100, 100, 200);
        block[2] = new Rectangle(400, 100, 100, 200);
        block[3] = new Rectangle(100, 300, 100, 200);
        block[4] = new Rectangle(400, 300, 100, 200);
        block[5] = new Rectangle(200, 300, 200, 100);
        block[6] = new Rectangle(200, 400, 100, 100);
        block[7] = new Rectangle(300, 400, 100, 100);
        block[8] = new Rectangle(100, 500, 100, 100);
        block[9] = new Rectangle(400, 500, 100, 100);

        border.setStyle("-fx-stroke: black; " +
            "-fx-stroke-width: 5; -fx-fill: lightgray");
        border.setEffect(new DropShadow(10, 10, 10, Color.GRAY));
        border.setArcWidth(15);
        border.setArcHeight(15);

        for (Rectangle b : block)
            b.setStyle(
                "-fx-stroke: Gainsboro; -fx-stroke-width: 5;");

        block[0].setFill(new ImagePattern(
            new Image("block_big.jpg")));

        for (int i = 1; i <= 4; i++)
            block[i].setFill(new ImagePattern(
                new Image("block_v.jpg")));

        block[5].setFill(new ImagePattern(
            new Image("block_h.jpg")));

        for (int i = 6; i <= 9; i++)
            block[i].setFill(new ImagePattern(
                new Image("block_small.jpg")));

        for (Rectangle b : block) {
            b.setArcWidth(20);
            b.setArcHeight(20);
            b.setEffect(new InnerShadow(15, 0, 0, Color.SILVER));
        }

        getChildren().add(border);
        for (int i = 0; i < 10; i++) {
            getChildren().add(block[i]);
        }
        getChildren().add(counter);

        // store the first pattern
        hashmap.add(hash());

        // handling mouse events
        // push the info into the stack when pressing the mouse
        // if the coordinates change, 
        // change the value, increase the count
        // if not, pop the peek
        for (Rectangle b : block) {
            b.setOnMouseDragged(e -> {
                if (e.getX() > b.getX() && canMoveRight(b))
                    b.setX(b.getX() + 100);
                if (e.getX() < b.getX() && canMoveLeft(b))
                    b.setX(b.getX() - 100);
                if (e.getY() > b.getY() && canMoveDown(b))
                    b.setY(b.getY() + 100);
                if (e.getY() < b.getY() && canMoveUp(b))
                    b.setY(b.getY() - 100);
            });

            b.setOnMousePressed(e -> {
                infoStack.push(
                    new MoveInfo(RectangleUtility.getIndex(b, block),
                    b.getX(), b.getY(), b.getX(), b.getY()));
            });

            b.setOnMouseReleased(e -> {
                if (infoStack.peek().getBeginX() != b.getX() ||
                    infoStack.peek().getBeginY() != b.getY()) {
                    infoStack.peek().setEndX(b.getX());
                    infoStack.peek().setEndY(b.getY());
                    stepCount++;
                    counter.setText("Moves: \n"  + stepCount);
                }
                else
                    infoStack.pop();

                if (block[0].getX() == 200 && block[0].getY() == 400)
                    System.out.println("You win!");
            });
        }
    }

    // convert the parrern to a unique code
    // not meaning real hash...
    final public String hash() {
        String info = new String();
        int[][] square = new int[5][4];
        double tmpX;
        double tmpY;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                tmpX = j * 100 + 100;
                tmpY = i * 100 + 100;

                if ((tmpX == block[0].getX() &&
                    tmpY == block[0].getY()) ||
                    (tmpX == block[0].getX() + 100 &&
                    tmpY == block[0].getY()) ||
                    (tmpX == block[0].getX() &&
                    tmpY == block[0].getY() + 100) ||
                    (tmpX == block[0].getX() + 100 &&
                    tmpY == block[0].getY() + 100))
                    square[i][j] = 1;

                for (int k = 1; k <= 4; k++) {
                    if ((tmpX == block[k].getX() &&
                        tmpY == block[k].getY()) ||
                        (tmpX == block[k].getX() &&
                        tmpY == block[k].getY() + 100))
                        square[i][j] = 2;
                }

                if ((tmpX == block[5].getX() &&
                    tmpY == block[5].getY()) ||
                    (tmpX == block[5].getX() + 100 &&
                    tmpY == block[5].getY()))
                    square[i][j] = 3;

                for (int k = 6; k <= 9; k++) {
                    if (tmpX == block[k].getX() &&
                        tmpY == block[k].getY())
                        square[i][j] = 4;
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                info += square[i][j];
            }
        }

        return info;
    }

    public int getStepCount() {
        return stepCount;
    }

    public ArrayList<String> getHashmap() {
        return hashmap;
    }

    public Node<MoveInfo> getInfoTree() {
        return infoTree;
    }
    
    public Stack<MoveInfo> getInfoStack() {
        return infoStack;
    }

    public Rectangle[] getBlock() {
        return block;
    }

    // reset all the blocks
    // clear the stack 
    // reset the counter
    public void reset() {
        block[0].setX(200);
        block[0].setY(100);
        block[1].setX(100);
        block[1].setY(100);
        block[2].setX(400);
        block[2].setY(100);
        block[3].setX(100);
        block[3].setY(300);
        block[4].setX(400);
        block[4].setY(300);
        block[5].setX(200);
        block[5].setY(300);
        block[6].setX(200);
        block[6].setY(400);
        block[7].setX(300);
        block[7].setY(400);
        block[8].setX(100);
        block[8].setY(500);
        block[9].setX(400);
        block[9].setY(500);

        while (!infoStack.empty())
            infoStack.pop();

        stepCount = 0;
        counter.setText("Moves: \n"  + stepCount);
    }

    // set the coordinates back
    // pop the peek from the stack
    // counter - 1
    public void undo() {
        if (!infoStack.empty()) {
            block[infoStack.peek().getIndex()].setX(
                infoStack.peek().getBeginX());
            block[infoStack.peek().getIndex()].setY(
                infoStack.peek().getBeginY());
            infoStack.pop();
            stepCount--;
            counter.setText("Moves: \n"  + stepCount);
        }
    }

    // method used to check if one block can be moved
    final public boolean canMoveUp(Rectangle b) {
        boolean isClear = true;

        if (b.getY() == 100)
            isClear = false;
        else {
            for (int i = 0; i < block.length; i++) {
                if ((b.getY() == block[i].getY() +
                    block[i].getHeight() &&
                    !(b.getX() >= block[i].getX() +
                    block[i].getWidth() ||
                    b.getX() + b.getWidth() <= block[i].getX()))) {
                    isClear = false;
                    i = block.length;
                }
            }
        }

        return isClear;
    }

    final public boolean canMoveDown(Rectangle b) {
        boolean isClear = true;

        if (b.getY() + b.getHeight() == 600)
            isClear = false;
        else {
            for (int i = 0; i < block.length; i++) {
                if ((b.getY() + b.getHeight() == block[i].getY() &&
                    !(b.getX() >= block[i].getX() + 
                    block[i].getWidth() ||
                    b.getX() + b.getWidth() <= block[i].getX()))) {
                    isClear = false;
                    i = block.length;
                }
            }
        }

        return isClear;
    }

    final public boolean canMoveLeft(Rectangle b) {
        boolean isClear = true;

        if (b.getX() == 100)
            isClear = false;
        else {
            for (int i = 0; i < block.length; i++) {
                if ((b.getX() == block[i].getX() + 
                    block[i].getWidth() &&
                    !(b.getY() >= block[i].getY() +
                    block[i].getHeight() ||
                    b.getY() + b.getHeight() <= block[i].getY()))) {
                    isClear = false;
                    i = block.length;
                }
            }
        }

        return isClear;
    }

    final public boolean canMoveRight(Rectangle b) {
        boolean isClear = true;

        if (b.getX() + b.getWidth() == 500)
            isClear = false;
        else {
            for (int i = 0; i < block.length; i++) {
                if ((b.getX() + b.getWidth() == block[i].getX() &&
                    !(b.getY() >= block[i].getY() + 
                    block[i].getHeight() ||
                    b.getY() + b.getHeight() <= block[i].getY()))) {
                    isClear = false;
                    i = block.length;
                }
            }
        }

        return isClear;
    }

    // return the empty blocks (the 2 blocks not being used)
    public ArrayList<Rectangle> emptySpace() {
        ArrayList<Rectangle> usedSpace = new ArrayList<>();
        ArrayList<Rectangle> allSpace = new ArrayList<>();
        ArrayList<Rectangle> emptySpace = new ArrayList<>();
        boolean notUsed;

        for (int i = 0; i < 5; i++) { // all the cells
            for (int j = 0; j < 4; j++) {
                allSpace.add(new Rectangle(
                    j * 100 + 100, i * 100 + 100 ,100, 100));
            }
        }

        for (int i = 0; i < 2; i++) { // cell 0 used
            for (int j = 0; j < 2; j++) {
                usedSpace.add(new Rectangle(
                    j * 100 + block[0].getX(),
                    i * 100 + block[0].getY(), 100, 100));
            }
        }

        for (int i = 1; i <= 4; i++) { // cell 1-4 used
            for (int j = 0; j < 2; j++) {
                usedSpace.add(new Rectangle(block[i].getX(),
                    j * 100 + block[i].getY(), 100, 100));
            }
        }

        for (int i = 0; i < 2; i++) { // cell 5 used
            usedSpace.add(new Rectangle(i * 100 + block[5].getX(),
                block[5].getY(), 100, 100));
        }

        for (int i = 6; i <= 9; i++) { // cell 6-9 used
            usedSpace.add(new Rectangle(block[i].getX(),
                block[i].getY(), 100, 100));
        }

        for (int i = 0; i < allSpace.size(); i++) { // get empty cells
            notUsed = true;

            for (int j = 0; j < usedSpace.size(); j++) {
                if (allSpace.get(i).getX() == 
                    usedSpace.get(j).getX() &&
                    allSpace.get(i).getY() ==
                    usedSpace.get(j).getY()) {
                    notUsed = false;
                    j = usedSpace.size();
                }
            }

            if (notUsed) {
                emptySpace.add(allSpace.get(i));
            }
        }

        return emptySpace;
    }

    // check the two empty blocks
    // if they are not next to each other, return 0
    // if they are next to each other horizontally, return 1
    // if they are next to each other vertically, return 2
    public int emptyStatus() {
        ArrayList<Rectangle> emptySpace = emptySpace();
        double xd = emptySpace.get(0).getX() -
            emptySpace.get(1).getX();
        double yd = emptySpace.get(0).getY() - 
            emptySpace.get(1).getY();
        int status;

        xd = xd < 0 ? -xd : xd;
        yd = yd < 0 ? -yd : yd;

        if (xd == 100 && yd == 0)
            status = 1;
        else if (xd == 0 && yd == 100)
            status = 2;
        else
            status = 0;

        return status;
    }

    // find all movable blocks
    public ArrayList<Rectangle> findMovable() {
        ArrayList<Rectangle> movableBlocks = new ArrayList<>();

        for (Rectangle i : block) {
            if (canMoveUp(i) || canMoveDown(i) ||
                canMoveLeft(i) || canMoveRight(i))
                movableBlocks.add(i);
        }

        return movableBlocks;
    }

    // find the possible moving positions of one block
    public ArrayList<String> findDirectionsOf(Rectangle b) {
        ArrayList<String> possibleDirections = new ArrayList<>();

        if (canMoveUp(b))
            possibleDirections.add("UP");
        if (canMoveDown(b))
            possibleDirections.add("DOWN");
        if (canMoveLeft(b))
            possibleDirections.add("LEFT");
        if (canMoveRight(b))
            possibleDirections.add("RIGHT");

        return possibleDirections;
    }

    //
    public ArrayList<MoveInfo> findNextMove() {
        ArrayList<MoveInfo> nextMove = new ArrayList<>();
        ArrayList<Rectangle> movable = findMovable();

        for (int i = 0; i < movable.size(); i++) {
            nextMove.addAll(findMoveOf(movable.get(i)));
        }

        return nextMove;
    }

    // find all the possible future moves 
    // and store them into the tree
    public ArrayList<MoveInfo> findMoveOf(Rectangle b) {
        ArrayList<String> possibleDirections = findDirectionsOf(b);
        ArrayList<MoveInfo> possibleMoves = new ArrayList<>();
        ArrayList<Rectangle> emptySpace = emptySpace();
        int emptyStatus = emptyStatus();

        if (possibleDirections.size() == 2) {
            if (b.getWidth() == 200) {
                possibleMoves.add(
                    new MoveInfo(RectangleUtility.getIndex(b, block),
                        b.getX(), b.getY(),
                        b.getX() - 100, b.getY()));
                possibleMoves.add(
                    new MoveInfo(RectangleUtility.getIndex(b, block),
                        b.getX(), b.getY(),
                        b.getX() + 100, b.getY()));
            }
            else if (b.getHeight() == 200) {
                possibleMoves.add(
                    new MoveInfo(RectangleUtility.getIndex(b, block),
                        b.getX(), b.getY(),
                        b.getX(), b.getY() - 100));
                possibleMoves.add(
                    new MoveInfo(RectangleUtility.getIndex(b, block),
                        b.getX(), b.getY(),
                        b.getX(), b.getY() + 100));
            }
            else {
                possibleMoves.add(
                    new MoveInfo(RectangleUtility.getIndex(b, block),
                        b.getX(), b.getY(),
                        emptySpace.get(0).getX(),
                        emptySpace.get(0).getY()));
                possibleMoves.add(
                    new MoveInfo(RectangleUtility.getIndex(b, block),
                        b.getX(), b.getY(),
                        emptySpace.get(1).getX(),
                        emptySpace.get(1).getY()));
            }
        }
        else if (possibleDirections.size() == 1) {
            if (b.getWidth() == 200 && b.getHeight() == 200) {
                if (possibleDirections.get(0).equals("UP"))
                    possibleMoves.add(
                        new MoveInfo(
                            RectangleUtility.getIndex(b, block),
                            b.getX(), b.getY(),
                            b.getX(), b.getY() - 100));
                else if (possibleDirections.get(0).equals("DOWN"))
                    possibleMoves.add(
                        new MoveInfo(
                            RectangleUtility.getIndex(b, block),
                            b.getX(), b.getY(),
                            b.getX(), b.getY() + 100));
                else if (possibleDirections.get(0).equals("LEFT"))
                    possibleMoves.add(
                        new MoveInfo(
                            RectangleUtility.getIndex(b, block),
                            b.getX(), b.getY(),
                            b.getX() - 100, b.getY()));
                else
                    possibleMoves.add(
                        new MoveInfo(
                            RectangleUtility.getIndex(b, block),
                            b.getX(), b.getY(),
                            b.getX() + 100, b.getY()));
            }
            else if (b.getWidth() == 200 && b.getHeight() == 100) {
                if (possibleDirections.get(0).equals("UP"))
                    possibleMoves.add(
                        new MoveInfo(
                            RectangleUtility.getIndex(b, block),
                            b.getX(), b.getY(),
                            b.getX(), b.getY() - 100));
                else if (possibleDirections.get(0).equals("DOWN"))
                    possibleMoves.add(
                        new MoveInfo(
                            RectangleUtility.getIndex(b, block),
                            b.getX(), b.getY(),
                            b.getX(), b.getY() + 100));
                else {
                    if (emptyStatus == 1) {
                        if (possibleDirections.get(0).equals("LEFT")) {
                            possibleMoves.add(
                                new MoveInfo(
                                    RectangleUtility.getIndex(b, block),
                                    b.getX(), b.getY(),
                                    b.getX() - 100, b.getY()));
                            possibleMoves.add(
                                new MoveInfo(
                                    RectangleUtility.getIndex(b, block),
                                    b.getX(), b.getY(),
                                    b.getX() - 200, b.getY()));
                        }
                        else {
                            possibleMoves.add(
                                new MoveInfo(
                                    RectangleUtility.getIndex(b, block),
                                    b.getX(), b.getY(),
                                    b.getX() + 100, b.getY()));
                            possibleMoves.add(
                                new MoveInfo(
                                    RectangleUtility.getIndex(b, block),
                                    b.getX(), b.getY(),
                                    b.getX() + 200, b.getY()));
                        }
                    }
                    else {
                        if (possibleDirections.get(0).equals("LEFT"))
                            possibleMoves.add(
                                new MoveInfo(
                                    RectangleUtility.getIndex(b, block),
                                    b.getX(), b.getY(),
                                    b.getX() - 100, b.getY()));
                        else
                            possibleMoves.add(
                                new MoveInfo(
                                    RectangleUtility.getIndex(b, block),
                                    b.getX(), b.getY(),
                                    b.getX() + 100, b.getY()));
                    }
                }
            }
            else if (b.getWidth() == 100 && b.getHeight() == 200) {
                if (possibleDirections.get(0).equals("LEFT"))
                    possibleMoves.add(
                        new MoveInfo(
                            RectangleUtility.getIndex(b, block),
                            b.getX(), b.getY(),
                            b.getX() - 100, b.getY()));
                else if (possibleDirections.get(0).equals("RIGHT"))
                    possibleMoves.add(
                        new MoveInfo(
                            RectangleUtility.getIndex(b, block),
                            b.getX(), b.getY(),
                            b.getX() + 100, b.getY()));
                else {
                    if (emptyStatus == 2) {
                        if (possibleDirections.get(0).equals("UP")) {
                            possibleMoves.add(
                                new MoveInfo(
                                    RectangleUtility.getIndex(b, block),
                                    b.getX(), b.getY(),
                                    b.getX(), b.getY() - 100));
                            possibleMoves.add(
                                new MoveInfo(
                                    RectangleUtility.getIndex(b, block),
                                    b.getX(), b.getY(),
                                    b.getX(), b.getY() - 200));
                        }
                        else {
                            possibleMoves.add(
                                new MoveInfo(
                                    RectangleUtility.getIndex(b, block),
                                    b.getX(), b.getY(),
                                    b.getX(), b.getY() + 100));
                            possibleMoves.add(
                                new MoveInfo(
                                    RectangleUtility.getIndex(b, block),
                                    b.getX(), b.getY(),
                                    b.getX(), b.getY() + 200));
                        }
                    }
                    else {
                        if (possibleDirections.get(0).equals("UP"))
                            possibleMoves.add(
                                new MoveInfo(
                                    RectangleUtility.getIndex(b, block),
                                    b.getX(), b.getY(),
                                    b.getX(), b.getY() - 100));
                        else
                            possibleMoves.add(
                                new MoveInfo(
                                    RectangleUtility.getIndex(b, block),
                                    b.getX(), b.getY(),
                                    b.getX(), b.getY() + 100));
                    }
                }
            }
            else {
                if (emptyStatus == 0) {
                    if (possibleDirections.get(0).equals("UP"))
                        possibleMoves.add(
                            new MoveInfo(
                                RectangleUtility.getIndex(b, block),
                                b.getX(), b.getY(),
                                b.getX(), b.getY() - 100));
                    else if (possibleDirections.get(0).equals("DOWN"))
                        possibleMoves.add(
                            new MoveInfo(
                                RectangleUtility.getIndex(b, block),
                                b.getX(), b.getY(),
                                b.getX(), b.getY() + 100));
                    else if (possibleDirections.get(0).equals("LEFT"))
                        possibleMoves.add(
                            new MoveInfo(
                                RectangleUtility.getIndex(b, block),
                                b.getX(), b.getY(),
                                b.getX() - 100, b.getY()));
                    else
                        possibleMoves.add(
                            new MoveInfo(
                                RectangleUtility.getIndex(b, block),
                                b.getX(), b.getY(),
                                b.getX() + 100, b.getY()));
                }
                else {
                    possibleMoves.add(
                        new MoveInfo(
                            RectangleUtility.getIndex(b, block),
                            b.getX(), b.getY(),
                            emptySpace.get(0).getX(),
                            emptySpace.get(0).getY()));
                    possibleMoves.add(
                        new MoveInfo(
                            RectangleUtility.getIndex(b, block),
                            b.getX(), b.getY(),
                            emptySpace.get(1).getX(),
                            emptySpace.get(1).getY()));
                }
            }
        }

        return possibleMoves;
    }

    // get all the possible moves (predict)
    // store all the moves to the tree
    public void getAllMoves(Node<MoveInfo> tmpNode) {
        ArrayList<MoveInfo> nextMove = findNextMove();
        Boolean isRepeat;
        String hashCode;
        Rectangle tmpB;
        ArrayList<Integer> tmpList = new ArrayList<>();

        for (int i = 0; i < nextMove.size(); i++) {
            tmpNode.addChild(nextMove.get(i));
        }

        for (int i = 0; i < tmpNode.getChildren().size(); i++) {
            tmpB = block[tmpNode.getChildren().
                get(i).getData().getIndex()];
            infoStack.push(new MoveInfo(
                RectangleUtility.getIndex(tmpB, block),
                tmpB.getX(), tmpB.getY(),
                tmpNode.getChildren().get(i).getData().getEndX(),
                tmpNode.getChildren().get(i).getData().getEndY()));
            block[tmpNode.getChildren().get(i).getData().getIndex()].
                setX(tmpNode.getChildren().get(i).getData().getEndX());
            block[tmpNode.getChildren().get(i).getData().getIndex()].
                setY(tmpNode.getChildren().get(i).getData().getEndY());

            hashCode = hash();
            isRepeat = false;

            for (int j = 0; j < hashmap.size(); j++) {
                if (hashCode.equals(hashmap.get(j))) {
                    isRepeat = true;
                    j = hashmap.size();
                }
            }

            if (!isRepeat)
                hashmap.add(hashCode);
            else
                tmpList.add(i);

            undo();
        }
        
        // delete repeat patterns
        for (int i = 0; i < tmpList.size(); i++) { 
            tmpNode.getChildren().set(tmpList.get(i), null);
        }

        for (int i = 0; i < tmpNode.getChildren().size(); i++) {
            if (tmpNode.getChildren().get(i) == null) {
                tmpNode.getChildren().remove(i);
                i = -1;
            }
        }

        // recursive
        for (int i = 0; i < tmpNode.getChildren().size(); i++) { 
            tmpB = block[tmpNode.getChildren().
                get(i).getData().getIndex()];
            infoStack.push(new MoveInfo(
                RectangleUtility.getIndex(tmpB, block),
                tmpB.getX(), tmpB.getY(),
                tmpNode.getChildren().get(i).getData().getEndX(), 
                tmpNode.getChildren().get(i).getData().getEndY()));
            block[tmpNode.getChildren().get(i).getData().getIndex()].
                setX(tmpNode.getChildren().get(i).getData().getEndX());
            block[tmpNode.getChildren().get(i).getData().getIndex()].
                setY(tmpNode.getChildren().get(i).getData().getEndY());
 
            getAllMoves(tmpNode.getChildren().get(i));
        }
    }

    // need a method for searching the tree and get the solution
    // of least moves
}

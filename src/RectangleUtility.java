/**
 * Program Name: RectangleUtility.java
 * Discussion:   Rectangle Utility
 * Written By:   Zhiying Li
 * Date:         2016/12/12
 */

import javafx.scene.shape.Rectangle;

class RectangleUtility {
    public static int getIndex(Rectangle r, Rectangle[] rAry) {
        int index = -1;

        for (int i = 0; i < rAry.length; i++) {
            if (r == rAry[i]) {
                index = i;
                i = rAry.length;
            }
        }

        return index;
    }
}

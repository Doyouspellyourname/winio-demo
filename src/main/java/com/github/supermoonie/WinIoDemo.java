package com.github.supermoonie;

import static com.github.supermoonie.winio.VirtualKeyBoard.press;

/**
 * @author moonie
 * @date 2018/7/31
 */
public class WinIoDemo {

    public static void main(String[] args) {
        try {
            if (null == args || args.length == 0) {
                args = new String[]{"a", "b", "1", "@", "\\"};
            }
            press(args, 2000, 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }}

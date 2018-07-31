package com.github.supermoonie;

import com.github.supermoonie.user32.Win32Util;
import com.github.supermoonie.winio.VirtualKeyBoard;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

import static com.github.supermoonie.winio.VirtualKeyBoard.press;

/**
 * @author moonie
 * @date 2018/7/31
 */
public class WinIoDemo {

    public static void main(String[] args) {
        String driverPath = System.getProperty("user.home") + File.separator + "IEDriverServer.exe";
        extract(driverPath, "IEDriverServer.exe");
        System.setProperty("webdriver.ie.driver", driverPath);
        RemoteWebDriver driver = new InternetExplorerDriver();
        try {
            driver.get("https://pbsz.ebank.cmbchina.com/CmbBank_GenShell/UI/GenShellPC/Login/Login.aspx");
            driver.executeScript("showPassWordLogin();");
            WinDef.HWND textBox = Win32Util.findHandleByClassName(null, "ATL:.*", 10, TimeUnit.SECONDS);
            User32.INSTANCE.SetForegroundWindow(textBox);
            press("1234567890123456".split(""), 1000, 200);
            press(new String[]{"Tab", "1", "2", "3", "4", "5", "6"}, 1000, 200);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static void extract(final String fileName, final String name) {
        if (new File(fileName).exists()) {
            return;
        }
        try(InputStream inputStream = VirtualKeyBoard.class.getResourceAsStream("/" + name)) {
            Files.copy(inputStream, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

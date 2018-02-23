package xyz.supermoonie;

import com.sun.jna.platform.win32.WinDef.HWND;
import org.openqa.selenium.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import xyz.supermoonie.email.MailSender;
import xyz.supermoonie.image.ImageConverter;
import xyz.supermoonie.user32.Win32Util;
import xyz.supermoonie.winio.VKMapping;
import xyz.supermoonie.winio.VirtualKeyBoard;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static xyz.supermoonie.user32.User32Ext.USER32EXT;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) throws Exception {
        System.setProperty("webdriver.ie.driver","src/main/resources/IEDriverServer.exe");
        RemoteWebDriver wd = new InternetExplorerDriver();
        wd.get("https://nper.cmbc.com.cn/pweb/static/login.html");
        try {
            Set<Cookie> cookieSet = wd.manage().getCookies();
            for (Cookie cookie : cookieSet) {
                System.out.println(cookie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        wd.findElement(By.id("writeUserId")).sendKeys("1234567890");
        HWND hwnd = Win32Util.findHandleByClassName("Internet Explorer_Server", 10, TimeUnit.SECONDS);
        HWND edit = Win32Util.findHandleByClassName(hwnd, "ATL:Edit", 10, TimeUnit.SECONDS);
        USER32EXT.SwitchToThisWindow(edit, true);
        USER32EXT.SetFocus(edit);
        String pwd = "123456";
        for (int i = 0; i < pwd.length(); i ++) {
            VirtualKeyBoard.KeyPress(VKMapping.toScanCode(""+pwd.charAt(i)));
            Thread.sleep(1000L);
        }
        wd.executeScript("authenticateUser()");
        Wait<WebDriver> wait = new WebDriverWait(wd, 15);
        wait.until(ExpectedConditions.attributeContains(By.className("zlogo_tc"), "v-click", "logout()"));
        wd.executeScript("$('.v-binding').eq(12).click()");
        for (int i = 0; i < 15; i ++) {
            try {
                String text = wd.findElementById("transView").getText();
                if (text.contains("近10条")) {
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Thread.sleep(1000L);
        }
        Thread.sleep(5000L);
        wd.executeScript(" $('.xialali').eq(2).click()");
        Thread.sleep(10000L);
        BufferedImage image = wd.getScreenshotAs(new OutputType<BufferedImage>() {
            public BufferedImage convertFromBase64Png(String s) {
                BufferedImage image;
                try {
                    image = ImageConverter.base64ToImage(s);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                return image;
            }

            public BufferedImage convertFromPngBytes(byte[] bytes) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                try {
                    return ImageIO.read(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = dateFormat.format(new Date());
        String fileName = "D:\\selenium\\" +date+ ".png";
        ImageIO.write(image, "png", new File(fileName));
        String base64Img = ImageConverter.imageToBase64(image, "png");
        MailSender.send(base64Img, fileName);
        Thread.sleep(5000L);
        wd.close();
    }
}

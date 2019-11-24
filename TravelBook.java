package com.PhpTravel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import Excel.ExcelDataProvider;
import SnapShotUtility.SnapshotUtilty;

    public class TravelBook {
        static WebDriver driver;;;;;;;;;;;
        static WebDriverWait wait_10;
        static Properties prop=null;

        @BeforeSuite
        public void init() throws Exception {
            // String
            // path=System.getProperty("user.dir")+"\\Driver\\chromedriver.exe";
        	System.setProperty("webdriver.chrome.driver","D:\\DriverExe\\chromedriver.exe");
            // System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")
            // + "/Driver/chromedriver.exe");

        	//---------------------config propery file--------------------------------------------
        	prop=new Properties();
        	FileInputStream configFile=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\com\\Configuration\\TravelConfig.properties");
        	//FileInputStream configFile=new FileInputStream("D:\\workspace\\PhpTravelMavenFramework1\\TravelConfig.properties");
        	prop.load(configFile);
        	
            driver = new ChromeDriver();;;//Driver
           
            //driver.manage().window().maximize();
            // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            wait_10 = new WebDriverWait(driver, 60);
            driver.get("http://www.phptravels.net/admin");

        }

      // @Test(dataProvider = "QuickBook" ,dataProviderClass=ExcelDataProvider.class)
        
        //public static void bookTicket(String username,String password, String tax, int services,String registered,String CustNameValue,String cars,String Deposit,String paymentMethod,String dashBord) throws InterruptedException, IOException 
        
        //public static void bookTicket(String password,String tax,String services,String registered,String CustNameValue,String Dates,String cars,String Deposit,String paymentMethod,String dashBord) throws InterruptedException, Exception {
        @Test
        public static void bookTicket() throws IOException
        {
            wait_10.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("email"))));
            
            
            SnapshotUtilty.snapShots(driver, "BeforeLogin");

            // ---------------login fields
            driver.findElement(By.name("email")).sendKeys("admin@phptravels.com");
            driver.findElement(By.name("password")).sendKeys("Pass@123");

            // ---------------Login Button

            driver.findElement(By.xpath("//div[1]/form[1]/button")).click();
            
            SnapshotUtilty.snapShots(driver, "AfterLogin");

           
      // commented from quick book for jenkins build success test
          /*  //-----------------Quick Book
            new WebDriverWait(driver, 15)
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='content']/div[1]/div[1]/button/div/text()")));
            
            
            
            driver.findElement(By.xpath("//*[@id='content']/div[1]/div[1]/button/div/text()")).click();

            //----------------Apply Tax
            Select taxoption = new Select(driver.findElement(By.name("applytax")));
            System.out.println(taxoption.isMultiple());
            taxoption.selectByVisibleText(tax);
            
      
            // ------------Service
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(driver.findElement(By.name("service"))));
            Select service = new Select(driver.findElement(By.name("service")));
            service.selectByIndex(Integer.parseInt(services));

            //--------------Next Button
            driver.findElement(By.xpath(".//*[@id='quickbook']//div//button[2]")).click();

            //--------------Customer
            Select customer = new Select(driver.findElement(By.id("selusertype")));
            customer.selectByValue(registered);

            //--------------CustomerName

            Select CustName = new Select(driver.findElement(By.xpath(".//*[@id='regcust']/div/select")));
            CustName.selectByValue(CustNameValue);

            // -------------------------------------------------------Items---------------------------------
            // -------Date
            // wait_10.until(ExpectedConditions.elementToBeClickable(((By.xpath(".//*[@id='Cars']")))));
            driver.findElement(By.xpath(".//*[@id='bookingform']/div[3]/div[2]/div[1]/div")).click();

            wait_10.until(ExpectedConditions
                    .visibilityOf(driver.findElement(By.xpath("//div[3]/div[1]/table/tbody/tr/td"))));

            List<WebElement> dates = driver.findElements(By.xpath("//div[3]/div[1]/table/tbody/tr/td"));
            System.out.println(dates.size());
            for (WebElement selectdate : dates) {
                if (selectdate.getText().equals(Dates)) {
                    System.out.println(selectdate.getText());
                    selectdate.click();
                    break;
                }

            }
            // --------Car name
            Select Cars = new Select(driver.findElement(By.xpath(".//*[@id='bookingform']/div[3]//div/select")));
            Cars.selectByValue(cars);

            // Total Deposit xpath .//*[@id='totaltopay']
            wait_10.until(ExpectedConditions.presenceOfElementLocated(By.id("totaltopay")));
            WebElement cleartext = driver.findElement(By.id("totaltopay"));
            cleartext.clear();
            cleartext.sendKeys(Deposit);
            System.out.println("400");

            // -------------------------------------------------Extras----------------------------------------------------------------------------------------------------------------------------------------------------------
            wait_10.until(ExpectedConditions.elementToBeClickable(By.className("extras")));
            List<WebElement> extras = driver.findElements(By.className("extras"));
            System.out.println(extras.size());
            for (WebElement Extraopt : extras) {

                System.out.println(Extraopt.getText());
                Extraopt.click();
            }

            // --------------------------------------------------EXTRAS-------------------------------------------------------------------------------------------------------------

            // ------------------Payment Method ::Select paymentMethod =new
            // Select(driver.findElement(By.name("paymethod")));-----------------------------------------------------------------------------------

            wait_10.until(ExpectedConditions.elementToBeClickable(By.name("paymethod")));
            WebElement payMethod = driver.findElement(By.name("paymethod"));
            List<WebElement> PayOptions = payMethod.findElements(By.tagName("option"));
            System.out.println(PayOptions.size());
            for (WebElement paytype : PayOptions) {
                System.out.println("Pay Method" + paytype.getText());
                if (paytype.getText().equals(paymentMethod)) {
                    paytype.click();
                    break;
                }
            }

            // -----------------------------------------------------BookNow--------------------------------------------------------------------
            driver.findElement(By.xpath(".//*[@id='bookingform']/div[6]/div/input[2]")).click();
            System.out.println("Booking Done");
            WebElement Dashboard = driver.findElement(By.xpath("//nav/div[1]/a/span"));
            if (Dashboard.equals(dashBord)) {
                System.out.println("Booking Confirmed");
            }

            // ---------------------------------------------------Booking
            // ManagementPage------------------------------------------------------
            // String
            // Module=driver.findElement(By.xpath(".//*[@id='content']//tr[1]/td[6]")).getText();
            // List<WebElement> row =
            // driver.findElements(By.xpath(".//*[@id='content']//tr"));
            List<WebElement> col = driver.findElements(By.xpath(".//*[@id='content']//td"));
            // int i=1;
            // int j=6;
            // String
            // S=driver.findElement(By.xpath("//*[@id='content']//tr["+i+"]/td["+j+"]")).getText();
            // System.out.println(S);
            System.out.println(col.size());
            // System.out.println(row.size());
            for (int i = 1; i <= col.size(); i++) {

                String S = col.get(i).getText();
                System.out.println(S);

                if (S.equals("30")) {

                    driver.findElement(By.xpath(".//*[@id='30']/i")).click();
                    driver.switchTo().alert().accept();
                    System.out.println("Booking Deletead");
                    break;

                }

            }
            SnapshotUtilty.snapShots(driver, "Afterbooking");
            driver.quit();

            // ------------------------------------- Save
            // CSV------------------------------------------------------------
            
             * wait_10.until(ExpectedConditions.elementToBeClickable(By.xpath(
             * ".//*[@id='content']//div[1]/a[2]")));
             * driver.findElement(By.xpath(".//*[@id='content']//div[1]/a[2]")).
             * click(); Screen s =new Screen(); Pattern p = new
             * Pattern("D:\\print.PNG"); s.wait(p,1500000); try { s.click(p); }
             * catch (FindFailed e) { // TODO Auto-generated catch block
             * e.printStackTrace(); }
             

            // ----------------------------------Mouse
            // Actions-------------------------------------------------------------------
            Actions ae = new Actions(driver);
            wait_10.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[3]/ul[2]/li[2]/a/i")));
            WebElement mouseover = driver.findElement(By.xpath("//div[3]/ul[2]/li[2]/a/i"));
            Action mouseactions = ae.moveToElement(mouseover).build();
            mouseactions.perform();
            System.out.println("Mouse moved to pulldownmenu");

        }

        // -----------------------------------------MOUSE
        // EVENTS--------------------------------------------------
        
         * @Test(priority=2) public void mouseAndKeytest() { Actions ae=new
         * Actions(driver);
         * wait_10.until(ExpectedConditions.elementToBeClickable(By.xpath(
         * "//div[3]/ul[2]/li[2]/a/i"))); WebElement mouseover=
         * driver.findElement(By.xpath("//div[3]/ul[2]/li[2]/a/i")); Action
         * mouseactions =ae.moveToElement(mouseover).build();
         * mouseactions.perform(); System.out.println("Mouse moved to pulldownmenu"
         * );
         *
         *
         *
         * }
         

       
*/
            

        }
    }
    



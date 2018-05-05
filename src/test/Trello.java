package test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

public class Trello {
	final String baseURL = "http://wwww.trello.com";
	public WebDriver driver;

	@Test(priority = 0, enabled = false)
	public void createBoard() throws InterruptedException {

		driver.findElement(By.linkText("Create new board…")).click();

		driver.findElement(By.className("subtle-input")).click();
		driver.findElement(By.className("subtle-input")).clear();

		String boardName = "webDriver board";
		driver.findElement(By.className("subtle-input")).sendKeys(boardName);

		Thread.sleep(1000);
		driver.findElement(By.className("primary")).click();
		Thread.sleep(3000);

		Assert.assertEquals(boardName + " | Trello", driver.getTitle());
	}

	@Test(priority = 1, enabled = false)
	public void deleteBoard() throws InterruptedException {

		// open board name: webDriver board
		driver.findElement(
				By.xpath("//span[contains(@class,'board-tile-details-name') and contains(text(), 'webDriver board')]"))
				.click();

		Thread.sleep(1000); // More
		driver.findElement(By.className("js-open-more")).click();
		Thread.sleep(1000);

		// close boards
		driver.findElement(By.className("js-close-board")).click();
		Thread.sleep(1000);

		// close
		driver.findElement(By.className("js-confirm")).click();
		Thread.sleep(1000);

		// delete perm

		driver.findElement(By.linkText("Permanently Delete Board…")).click();
		Thread.sleep(1000);

		driver.findElement(By.className("js-confirm")).click();

		Thread.sleep(5000);

		Assert.assertTrue(driver.getPageSource().contains("Board not found."));
	}

	@Test(priority = 2, enabled = false)
	public void addTeam() throws InterruptedException {

		driver.findElement(By.linkText("Create a new team…")).click();

		Thread.sleep(1000);
		String teamName = "webDriver team";
		driver.findElement(By.id("org-display-name")).sendKeys(teamName);

		Thread.sleep(100);
		driver.findElement(By.className("primary")).click();

		Thread.sleep(5000);
		Assert.assertTrue(driver.getTitle().contains(teamName));
	}

	@Test(priority = 3, enabled = false)
	public void deleteTeam() throws InterruptedException {

		driver.findElement(By.className("boards-page-board-section-header-options-item")).click();
		Thread.sleep(5000);

		driver.findElement(By.className("js-org-account")).click();
		Thread.sleep(1000);
		driver.findElement(By.linkText("Delete this team?")).click();
		Thread.sleep(1000);
		driver.findElement(By.className("js-confirm")).click();

		Thread.sleep(5000);
		Assert.assertEquals(driver.getTitle(), "Boards | Trello");
	}

	@Test(priority = 4, enabled = true)
	public void addListAndCard() throws InterruptedException {
		// open board name: webDriver board
		driver.findElement(
				By.xpath("//span[contains(@class,'board-tile-details-name') and contains(text(), 'webDriver board')]"))
				.click();

		Thread.sleep(1000);

		// TODO: if the list is not the fist ..

		driver.findElement(By.className("list-name-input")).sendKeys("List 1");
		driver.findElement(By.className("primary")).click();
		Thread.sleep(1000);

		WebElement element = driver.findElement(By.className("phenom-desc"));
		Assert.assertEquals("Seif Gamal added List 1 to this board", element.getText());

		Thread.sleep(1000);

		driver.findElement(By.className("js-card-title")).sendKeys("card 1");
		driver.findElement(By.className("primary")).click();

		Thread.sleep(2000);

		element = driver.findElement(By.className("phenom-desc"));
		Assert.assertEquals(element.getText(), "Seif Gamal added card 1 to List 1");
	}

	@Test(priority = 5, enabled = true)
	public void ArchieveList() throws InterruptedException {
		// open board name: webDriver board
		driver.findElement(
				By.xpath("//span[contains(@class,'board-tile-details-name') and contains(text(), 'webDriver board')]"))
				.click();

		Thread.sleep(1000);

		driver.findElement(By.className("js-open-list-menu")).click();
		driver.findElement(By.className("js-close-list")).click();
		Thread.sleep(1000);

		// get Activity
		WebElement element = driver.findElement(By.className("phenom-desc"));
		Assert.assertEquals("Seif Gamal archived list List 1", element.getText());
	}

	@BeforeMethod(enabled = true)
	public void getBaseURL() throws InterruptedException {
		driver.get(baseURL);
		Thread.sleep(3000);
	}

	@BeforeTest
	public void beforeTest() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseURL);
		Thread.sleep(1000);

		driver.findElement(By.linkText("Log In")).click();

		driver.findElement(By.id("user")).sendKeys("seif_gamal09@yahoo.com");

		driver.findElement(By.id("password")).sendKeys("*********");

		driver.findElement(By.id("login")).click();
		Thread.sleep(2000);
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Playwright
import com.microsoft.playwright.options.LoadState
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue


class GoogleSearchTest {
    companion object {
        lateinit var playwright: Playwright
        lateinit var browser: Browser

        @BeforeAll
        @JvmStatic
        fun setUp() {
            playwright = Playwright.create()
            val chromium = playwright.chromium()
            browser = chromium.launch(
                BrowserType.LaunchOptions().setHeadless(false)
            )
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            if (::browser.isInitialized) {
                browser.close()
                playwright.close()
            }
        }
    }

    @Test
    fun testGoogleSearchForCat() {
        synchronized(this) { // Синхронизация доступа к `browser`
            val page = browser.newPage()
            page.navigate("https://www.bing.com/")
            page.waitForLoadState(LoadState.DOMCONTENTLOADED)

            val searchInput = page.querySelector("#sb_form_q") // Селектор поля ввода
            searchInput.fill("котик")// Ввод поискового запроса
            searchInput.press("Enter") // Нажатие Enter

            page.waitForLoadState(LoadState.NETWORKIDLE) // Ожидание загрузки страницы

            val expectedText = "котик" // Ожидаемый текст в результатах поиска
            val element = page.querySelector(".b_topTitle.mmtitle")
            val elementText = element.textContent()

            // Проверить, что элемент содержит текст "котик"
            assertTrue(elementText.contains(expectedText), "Элемент не содержит текст 'котик'")

            page.close()
        }
    }
}
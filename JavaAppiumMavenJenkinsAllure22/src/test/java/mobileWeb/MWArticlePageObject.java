package mobileWeb;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:footer";
        OPTIONS_ADD_TO_MY_LIST = "css:#page-actions li#ca-watch button";
    }

    public MWArticlePageObject (RemoteWebDriver driver)
    {
        super(driver);
    }
}

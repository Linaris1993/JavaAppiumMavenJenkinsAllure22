package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    private  static final String name_of_folder = "Learning Programming";
    private static final String
    login = "TestMWAuth",
    password = "testov2024";
    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }
        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthBtn();
            Auth.enterLoginData(login, password);
            Auth.setSubmitForm();

            ArticlePageObject.waitForTitleElement();

            Assert.assertEquals("we are not on the same page after login.",
                    article_title,
                    ArticlePageObject.getArticleTitle()
            );

            ArticlePageObject.addArticleToMySaved();
        }
        ArticlePageObject.closeArticle();

            NavigationUI NavigationUI = NavigationUIFactory.get(driver);
            NavigationUI.setOpenNavigation();
            NavigationUI.clickMyLists();
            ArticlePageObject.swipeUpFunction();

            MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
                MyListPageObject.openFolderByName(name_of_folder);
        }
            MyListPageObject.swipeByArticleToDelete(article_title);
        }

    @Test
    public void testSavingTwoArticlesAndRemovingOne() {
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        String ArticleOne = "Java";
        String ArticleTwo = "Appium";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(ArticleOne);
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_ONE_title = ArticlePageObject.getArticleTitle();
        ArticlePageObject.saveArticle();
        ArticlePageObject.closeArticle();
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(ArticleTwo);
        SearchPageObject.clickByArticleWithSubstring("Automation for Apps");
        ArticlePageObject.waitForTitleElement();
        String article_TWO_title = ArticlePageObject.getArticleTitle();
        ArticlePageObject.saveArticle();
        ArticlePageObject.closeArticle();
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);
        MyListPageObject.openSavedArticles();
        ArticlePageObject.verifySavedArticles(article_ONE_title);
        ArticlePageObject.verifySavedArticles(article_TWO_title);
        MyListPageObject.swipeByArticleToDelete(article_ONE_title);
        ArticlePageObject.verifySavedArticles(ArticleTwo);
        SearchPageObject.clickByArticleWithSubstring(ArticleTwo);
        ArticlePageObject.waitForTitleElement();
        String notDeletedArticleTitle = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "Article is different, you probably removed wrong article",
                article_TWO_title,
                notDeletedArticleTitle
        );
}
}

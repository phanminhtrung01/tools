package com.pmt.tool.component;

import com.pmt.tool.entity.TSoftware;
import com.pmt.tool.entity.TSoftwareType;
import com.pmt.tool.entity.TTypeWork;
import com.pmt.tool.enums.SearchType;
import com.pmt.tool.repositories.SoftwareRepository;
import com.pmt.tool.repositories.SoftwareTypeRepository;
import com.pmt.tool.repositories.TypeWorkRepository;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class MyRunner implements CommandLineRunner {

    public static final Path resourceDirectory = Paths
            .get("src", "main", "resources", "static");
    private static final Logger LOGGER = LoggerFactory.getLogger(MyRunner.class);
    private static final String URL_SEARCH = "https://en.wikipedia.org";
    private static final String URL_SEARCH1 = "https://www.file-extensions.org";
    private final SoftwareTypeRepository softwareTypeRepository;
    private final SoftwareRepository softwareRepository;
    private final TypeWorkRepository typeWorkRepository;

    @Autowired
    public MyRunner(
            SoftwareTypeRepository softwareTypeRepository,
            SoftwareRepository softwareRepository,
            TypeWorkRepository typeWorkRepository) {
        this.softwareTypeRepository = softwareTypeRepository;
        this.softwareRepository = softwareRepository;
        this.typeWorkRepository = typeWorkRepository;
    }

    //PS: Search result on website: https://www.file-extensions.org
    public static @NotNull List<TSoftwareType> searchSoftwareType(
            String extensionType,
            @NotNull SearchType keySearch) throws IOException {

        List<TSoftwareType> softwareTypeList = new ArrayList<>();
        String searchString;
        Document documentSearch;
        int numberPage;

        if (keySearch.equals(SearchType.ALL)) {
            searchString = "/search/extensions/search/" + extensionType;
            documentSearch = Jsoup.connect(URL_SEARCH1 + searchString).get();
            Element elementPage = documentSearch
                    .getElementsByClass("pagenumber").first();

            if (elementPage != null) {
                Element lastPage = elementPage.getElementsByTag("a").last();
                assert lastPage != null;
                List<String> numbersPage = Arrays
                        .stream(lastPage.attr("href").split("/")).toList();
                numberPage = Integer.parseInt(numbersPage.get(numbersPage.size() - 1));
            } else
                numberPage = 1;

        } else {
            searchString = "/search/?searchstring=" + extensionType + "&searchtype=2";
            documentSearch = Jsoup.connect(URL_SEARCH1 + searchString).get();
            numberPage = 1;
        }

        for (int i = 1; i < numberPage + 1; i++) {
            if (i != 1) {
                String searchByPage = "/sortBy/extension/order/asc/page/" + numberPage;
                documentSearch = Jsoup
                        .connect(URL_SEARCH1 + searchString + searchByPage)
                        .get();
            }

            Element tableExtension = documentSearch.selectFirst(".extensiontable");
            assert tableExtension != null;
            Element itemsContainExtension = tableExtension.child(0).child(0);

            while (itemsContainExtension != null) {
                Elements itemExtension = itemsContainExtension.getElementsByTag("td");
                Element extensionResult = itemExtension.first();
                assert extensionResult != null;
                Element extensionContain = extensionResult
                        .getElementsByTag("strong")
                        .first();

                if (extensionContain == null) {
                    itemsContainExtension = itemsContainExtension.nextElementSibling();
                    continue;
                }
                
                if (extensionContain.text().equals(extensionType)) {
                    TSoftwareType softwareType = new TSoftwareType();
                    Element extensionDes = itemExtension.last();
                    assert extensionDes != null;

                    softwareType.setExtensionType(extensionType);
                    softwareType.setDescription(extensionDes.text());
                    softwareTypeList.add(softwareType);

                    if (keySearch.equals(SearchType.MOST))
                        break;
                }

                itemsContainExtension = itemsContainExtension.nextElementSibling();
            }
        }

        return softwareTypeList;
    }

    //PS: Scraping web: https://en.wikipedia.org
    private void insertSoftware() throws IOException {

        softwareRepository.deleteAll();

        Document documentSoftware = Jsoup
                .connect("https://www.microsoft.com/vi-vn/education/products/learning-tools")
                .get();
        Element softWareElement = documentSoftware
                .getElementsByClass("c-group")
                .last();
        assert softWareElement != null && softWareElement.hasText();
        Elements resultSoftware = softWareElement.getElementsByTag("h3");

        String product = "Microsoft ";
        resultSoftware.forEach(softWare -> {
            TSoftware software_ = new TSoftware();
            software_.setNameSoftware(product + softWare.text());

            LOGGER.info("insert [TSW]: " + softwareRepository.save(software_));
        });
    }

    //PS: Scraping web: https://en.wikipedia.org
    private void insertSoftwareType() throws IOException {

        softwareTypeRepository.deleteAll();

        List<TSoftware> softwareList = softwareRepository.findAll();

        String searchDoc = "/wiki/List_of_filename_extensions";
        Document documentHTML = Jsoup.connect(URL_SEARCH + searchDoc).get();
        Elements loopAlphabet = documentHTML.select(".hatnote a");

        loopAlphabet.forEach(alphabet -> {
            try {
                Document documentTypeSoftware = Jsoup
                        .connect(URL_SEARCH + alphabet.attr("href"))
                        .userAgent("Jsoup client")
                        .get();
                Elements tablesContain = documentTypeSoftware
                        .getElementsByClass("wikitable");

                tablesContain.forEach(tableContain -> {
                    Element docBodyContainResult = tableContain.getElementsByTag("tbody")
                            .first();
                    assert docBodyContainResult != null && docBodyContainResult.hasText();
                    Elements items = docBodyContainResult.getElementsByTag("tr");
                    items.remove(0);

                    items.forEach(item -> {
                        Element itemLast = Objects
                                .requireNonNull(item.getElementsByTag("td").last());

                        for (TSoftware software : softwareList) {
                            if (itemLast.text().contains(software.getNameSoftware())) {
                                TSoftwareType softwareType = new TSoftwareType();
                                Element itemFirst = Objects
                                        .requireNonNull(item.getElementsByTag("td").first());
                                Element itemMiddle = item.getElementsByTag("td").get(1);
                                assert itemMiddle != null && itemMiddle.hasText();

                                softwareType.setExtensionType(itemFirst.text().split("\\[")[0]);
                                softwareType.setDescription(itemMiddle.text().split("\\[")[0]);
                                softwareType.setSoftware(software);
                                LOGGER.info("insert [TSWT]: " + softwareTypeRepository.save(softwareType));
                                break;
                            }
                        }

                    });
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }

    //PS: Scraping web: https://www.file-extensions.org
    private void insertSoftware1() throws IOException {

        String searchDoc = "/filetype/extension/name/microsoft-office-files";

        Document documentSoftware = Jsoup
                .connect(URL_SEARCH1 + searchDoc)
                .get();
        Element softWareElement = documentSoftware
                .getElementsByTag("ul")
                .first();
        assert softWareElement != null;
        Elements resultSoftware = softWareElement.getElementsByTag("strong");

        String product = "Microsoft ";
        resultSoftware.forEach(softWare -> {
            TSoftware software_ = new TSoftware();
            software_.setNameSoftware(product + softWare.text().trim());

            LOGGER.info("insert [TSW]: " + softwareRepository.save(software_));
        });
    }

    //PS: Scraping web: https://www.file-extensions.org
    private void insertSoftwareType1() throws IOException {

        List<TSoftware> softwareList = softwareRepository.findAll();

        String searchDoc = "/filetype/extension/name/microsoft-office-files";
        Document documentHTML = Jsoup.connect(URL_SEARCH1 + searchDoc).get();
        Elements items = documentHTML.select("tr");
        items.remove(0);

        items.forEach(item -> {
            Element descriptionType = item.getElementsByTag("td").last();
            assert descriptionType != null;
            for (TSoftware software : softwareList) {
                if (descriptionType.text().contains(software.getNameSoftware())) {
                    TSoftwareType softwareType = new TSoftwareType();
                    Element extensionType = item.getElementsByTag("strong").first();
                    assert extensionType != null;

                    softwareType.setExtensionType(extensionType.text());
                    softwareType.setDescription(descriptionType.text());
                    softwareType.setSoftware(software);
                    LOGGER.info("insert [TSWT]: " + softwareTypeRepository.save(softwareType));
                    break;
                }
            }


        });
    }

    private void insertTypeWork(@NotNull List<String> argsNameTypeWork) {

        argsNameTypeWork = new ArrayList<>(argsNameTypeWork);
        argsNameTypeWork.addAll(Arrays.asList("handle", "feedback"));

        argsNameTypeWork.forEach((nameTypeWork) -> {
            TTypeWork typeWork = new TTypeWork();
            typeWork.setNameTypeWork(nameTypeWork);

            LOGGER.info("insert [TTW]: " + typeWorkRepository.save(typeWork));
        });
    }

    @Override
    public void run(String... args) throws IOException {

        /*insertSoftware1();*/
        /*insertSoftwareType1();*/
        /*insertTypeWork(List.of());*/
        searchSoftwareType("exe", SearchType.ALL);

    }
}

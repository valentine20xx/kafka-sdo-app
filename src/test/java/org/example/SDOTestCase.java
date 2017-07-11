package org.example;

import commonj.sdo.DataObject;
import commonj.sdo.helper.*;
import commonj.sdo.impl.HelperProvider;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SDOTestCase {
    private HelperContext ctx;

    private DataHelper dataHelper;
    private XSDHelper xsdHelper;
    private XMLHelper xmlHelper;
    private DataFactory dataFactory;
    private CopyHelper copyHelper;

    @Before
    public void init() {
        ctx = HelperProvider.getDefaultContext();

        dataHelper = ctx.getDataHelper();
        xsdHelper = ctx.getXSDHelper();
        xmlHelper = ctx.getXMLHelper();
        dataFactory = ctx.getDataFactory();
    }

    @Test
    public void test2() throws Exception {
        File xml = new File(getClass().getResource("/some.xml").getFile());

        FileInputStream inputStream = new FileInputStream(xml);

        XMLDocument xmlDocument = xmlHelper.load(inputStream);
        DataObject catalog = xmlDocument.getRootObject();

        List books = catalog.getList("book");
        System.out.println(books.size());

        Assert.assertEquals(12, books.size());
    }

    @Test
    public void test1() throws Exception {
        File xsd = new File(getClass().getResource("/test.xsd").getFile());
        File xml = new File(getClass().getResource("/test.xml").getFile());

        xsdHelper.define(new FileInputStream(xsd), null);

//        DataObject dataObject = dataFactory.create("http://test.ru", "SDO");
        XMLDocument xmlDocument = xmlHelper.load(new FileInputStream(xml));

        DataObject dataObject = xmlDocument.getRootObject();

        Assert.assertEquals("name", dataObject.getString("name"));
        Assert.assertEquals("surname", dataObject.getString("surname"));

        List list = dataObject.getList("listOfPassport/passport");
        Assert.assertEquals(2, list.size());
    }
}
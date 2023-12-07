package com.goldenkids.test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Jung {
    private String roadSectionId;
    private String fileCreateTime;
    private int cctvType;
    private String cctvUrl;
    private String cctvResolution;
    private double coordY;
    private String cctvFormat;
    private String cctvName;
    private double coordX;

    public Jung(String roadSectionId, String fileCreateTime, int cctvType, String cctvUrl, String cctvResolution, double coordY, String cctvFormat, String cctvName, double coordX) {
        this.roadSectionId = roadSectionId;
        this.fileCreateTime = fileCreateTime;
        this.cctvType = cctvType;
        this.cctvUrl = cctvUrl;
        this.cctvResolution = cctvResolution;
        this.coordY = coordY;
        this.cctvFormat = cctvFormat;
        this.cctvName = cctvName;
        this.coordX = coordX;
    }

    public String getRoadSectionId() {
        return roadSectionId;
    }

    public String getFileCreateTime() {
        return fileCreateTime;
    }

    public int getCctvType() {
        return cctvType;
    }

    public String getCctvUrl() {
        return cctvUrl;
    }

    public String getCctvResolution() {
        return cctvResolution;
    }

    public double getCoordY() {
        return coordY;
    }

    public String getCctvFormat() {
        return cctvFormat;
    }

    public String getCctvName() {
        return cctvName;
    }

    public double getCoordX() {
        return coordX;
    }

    public static ArrayList<Jung> parseJungData(String xmlData) {
        ArrayList<Jung> jungList = new ArrayList<>();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            ByteArrayInputStream input = new ByteArrayInputStream(xmlData.getBytes("UTF-8"));

            XMLHandler xmlHandler = new XMLHandler();
            saxParser.parse(input, xmlHandler);

            jungList = xmlHandler.getJungList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jungList;
    }

    private static class XMLHandler extends DefaultHandler {
        private ArrayList<Jung> jungList;
        private Jung currentJung;
        private StringBuilder data;

        public ArrayList<Jung> getJungList() {
            return jungList;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            data = new StringBuilder();

            if (qName.equalsIgnoreCase("data")) {
                currentJung = new Jung("", "", 0, "", "", 0.0, "", "", 0.0);
                if (jungList == null) {
                    jungList = new ArrayList<>();
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            data.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (currentJung != null) {
                String value = data.toString().trim();

                switch (qName.toLowerCase()) {
                    case "roadsectionid":
                        currentJung.roadSectionId = value;
                        break;
                    case "filecreatetime":
                        currentJung.fileCreateTime = value;
                        break;
                    case "cctvtype":
                        currentJung.cctvType = Integer.parseInt(value);
                        break;
                    case "cctvurl":
                        currentJung.cctvUrl = value;
                        break;
                    case "cctvresolution":
                        currentJung.cctvResolution = value;
                        break;
                    case "coordy":
                        currentJung.coordY = Double.parseDouble(value);
                        break;
                    case "cctvformat":
                        currentJung.cctvFormat = value;
                        break;
                    case "cctvname":
                        currentJung.cctvName = value;
                        break;
                    case "coordx":
                        currentJung.coordX = Double.parseDouble(value);
                        break;
                    case "data":
                        jungList.add(currentJung);
                        break;
                }
            }
        }
    }
}

package com.productList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Map;

import java.util.HashMap;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
    public class Store {
        private EcommercePlatform amazon;
        private EcommercePlatform flipkart;
        private EcommercePlatform snapdeal;
        private EcommercePlatform ebay;
        private EcommercePlatform paytm;
        private EcommercePlatform croma;
        private EcommercePlatform yebhi;
        private EcommercePlatform indiatimes;
        private EcommercePlatform homeshop18;
        private EcommercePlatform naaptol;
        private EcommercePlatform infibeam;
        private EcommercePlatform tatacliq;
        private EcommercePlatform shopclues;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();


        public EcommercePlatform getAmazon() {
            return amazon;
        }

        public void setAmazon(EcommercePlatform amazon) {
            this.amazon = amazon;
        }

        public EcommercePlatform getFlipkart() {
            return flipkart;
        }

        public void setFlipkart(EcommercePlatform flipkart) {
            this.flipkart = flipkart;
        }

        public EcommercePlatform getSnapdeal() {
            return snapdeal;
        }

        public void setSnapdeal(EcommercePlatform snapdeal) {
            this.snapdeal = snapdeal;
        }

        public EcommercePlatform getEbay() {
            return ebay;
        }

        public void setEbay(EcommercePlatform ebay) {
            this.ebay = ebay;
        }

        public EcommercePlatform getPaytm() {
            return paytm;
        }

        public void setPaytm(EcommercePlatform paytm) {
            this.paytm = paytm;
        }

        public EcommercePlatform getCroma() {
            return croma;
        }

        public void setCroma(EcommercePlatform croma) {
            this.croma = croma;
        }

        public EcommercePlatform getYebhi() {
            return yebhi;
        }

        public void setYebhi(EcommercePlatform yebhi) {
            this.yebhi = yebhi;
        }

        public EcommercePlatform getIndiatimes() {
            return indiatimes;
        }

        public void setIndiatimes(EcommercePlatform indiatimes) {
            this.indiatimes = indiatimes;
        }

        public EcommercePlatform getHomeshop18() {
            return homeshop18;
        }

        public void setHomeshop18(EcommercePlatform homeshop18) {
            this.homeshop18 = homeshop18;
        }

        public EcommercePlatform getNaaptol() {
            return naaptol;
        }

        public void setNaaptol(EcommercePlatform naaptol) {
            this.naaptol = naaptol;
        }

        public EcommercePlatform getInfibeam() {
            return infibeam;
        }

        public void setInfibeam(EcommercePlatform infibeam) {
            this.infibeam = infibeam;
        }

        public EcommercePlatform getTatacliq() {
            return tatacliq;
        }

        public void setTatacliq(EcommercePlatform tatacliq) {
            this.tatacliq = tatacliq;
        }

        public EcommercePlatform getShopclues() {
            return shopclues;
        }

        public void setShopclues(EcommercePlatform shopclues) {
            this.shopclues = shopclues;
        }

        public void setAdditionalProperties(Map<String, Object> additionalProperties) {
            this.additionalProperties = additionalProperties;
        }



        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

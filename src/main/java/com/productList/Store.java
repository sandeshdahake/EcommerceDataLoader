package com.productList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Map;

import java.util.HashMap;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
    public class Store {
    public EcommercePlatform getEcommercePlatform() {
        return ecommercePlatform;
    }



        private EcommercePlatform ecommercePlatform;
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

        @JsonProperty("amazon")
        public EcommercePlatform getAmazon() {
            return ecommercePlatform;
        }

        public void setAmazon(EcommercePlatform amazon) {
            this.ecommercePlatform = amazon;
        }
        @JsonProperty("flipkart")
        public EcommercePlatform getFlipkart() {
            return ecommercePlatform;
        }

        public void setFlipkart(EcommercePlatform flipkart) {
            this.ecommercePlatform = flipkart;
        }
        @JsonProperty("snapdeal")
        public EcommercePlatform getSnapdeal() {
            return ecommercePlatform;
        }

        public void setSnapdeal(EcommercePlatform snapdeal) {
            this.ecommercePlatform = snapdeal;
        }
    @JsonProperty("ebay")
        public EcommercePlatform getEbay() {
            return ebay;
        }

        public void setEbay(EcommercePlatform ebay) {
            this.ecommercePlatform = ebay;
        }
    @JsonProperty("paytm")
        public EcommercePlatform getPaytm() {
            return ecommercePlatform;
        }

        public void setPaytm(EcommercePlatform paytm) {
            this.ecommercePlatform = paytm;
        }
        @JsonProperty("croma")
        public EcommercePlatform getCroma() {
            return ecommercePlatform;
        }

        public void setCroma(EcommercePlatform croma) {
            this.ecommercePlatform = croma;
        }
        @JsonProperty("yebhi")
        public EcommercePlatform getYebhi() {
            return ecommercePlatform;
        }

        public void setYebhi(EcommercePlatform yebhi) {
            this.ecommercePlatform = yebhi;
        }
        @JsonProperty("indiatimes")
        public EcommercePlatform getIndiatimes() {
            return ecommercePlatform;
        }

        public void setIndiatimes(EcommercePlatform indiatimes) {
            this.ecommercePlatform = indiatimes;
        }
        /*@JsonProperty("indiatimes")
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
*/      @JsonProperty("shopclues")
        public EcommercePlatform getShopclues() {
            return ecommercePlatform;
        }

        public void setShopclues(EcommercePlatform shopclues) {
            this.ecommercePlatform = shopclues;
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

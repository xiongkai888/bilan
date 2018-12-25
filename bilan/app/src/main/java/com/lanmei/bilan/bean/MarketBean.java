package com.lanmei.bilan.bean;

/**
 * Created by xkai on 2018/6/26.
 */

public class MarketBean {

    /**
     * id : 1
     * name : Bitcoin
     * symbol : BTC
     * website_slug : bitcoin
     * rank : 1
     * circulating_supply : 17115950
     * total_supply : 17115950
     * max_supply : 21000000
     * quotes : {"USD":{"price":6272.42,"volume_24h":5637260000,"market_cap":107358427099,"percent_change_1h":0.37,"percent_change_24h":1.7,"percent_change_7d":-6.88}}
     * last_updated : 1529990677
     */

    private int id;
    private String name;
    private String symbol;
    private String website_slug;
    private int rank;
    private int circulating_supply;
    private double total_supply;
    private int max_supply;
    private QuotesBean quotes;
    private int last_updated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getWebsite_slug() {
        return website_slug;
    }

    public void setWebsite_slug(String website_slug) {
        this.website_slug = website_slug;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCirculating_supply() {
        return circulating_supply;
    }

    public void setCirculating_supply(int circulating_supply) {
        this.circulating_supply = circulating_supply;
    }

    public double getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(double total_supply) {
        this.total_supply = total_supply;
    }

    public int getMax_supply() {
        return max_supply;
    }

    public void setMax_supply(int max_supply) {
        this.max_supply = max_supply;
    }

    public QuotesBean getQuotes() {
        return quotes;
    }

    public void setQuotes(QuotesBean quotes) {
        this.quotes = quotes;
    }

    public int getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(int last_updated) {
        this.last_updated = last_updated;
    }

    public static class QuotesBean {
        /**
         * USD : {"price":6272.42,"volume_24h":5637260000,"market_cap":107358427099,"percent_change_1h":0.37,"percent_change_24h":1.7,"percent_change_7d":-6.88}
         */

        private USDBean USD;

        public USDBean getUSD() {
            return USD;
        }

        public void setUSD(USDBean USD) {
            this.USD = USD;
        }

        public static class USDBean {
            /**
             * price : 6272.42
             * volume_24h : 5637260000
             * market_cap : 107358427099
             * percent_change_1h : 0.37
             * percent_change_24h : 1.7
             * percent_change_7d : -6.88
             */

            private double price;
            private long volume_24h;
            private long market_cap;
            private double percent_change_1h;
            private double percent_change_24h;
            private double percent_change_7d;

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public long getVolume_24h() {
                return volume_24h;
            }

            public void setVolume_24h(long volume_24h) {
                this.volume_24h = volume_24h;
            }

            public long getMarket_cap() {
                return market_cap;
            }

            public void setMarket_cap(long market_cap) {
                this.market_cap = market_cap;
            }

            public double getPercent_change_1h() {
                return percent_change_1h;
            }

            public void setPercent_change_1h(double percent_change_1h) {
                this.percent_change_1h = percent_change_1h;
            }

            public double getPercent_change_24h() {
                return percent_change_24h;
            }

            public void setPercent_change_24h(double percent_change_24h) {
                this.percent_change_24h = percent_change_24h;
            }

            public double getPercent_change_7d() {
                return percent_change_7d;
            }

            public void setPercent_change_7d(double percent_change_7d) {
                this.percent_change_7d = percent_change_7d;
            }
        }
    }
}

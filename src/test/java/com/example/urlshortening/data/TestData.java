package com.example.urlshortening.data;

public class TestData {

  private static final Long BASE62_MAX_VALUE = 218340105584895L;

  public static String[] VALID_URI = new String[]{
      "https://github.com/Hyune-c/url-shortening/issues/3",
      "https://www.google.com/search?q=url+%EC%B6%95%EC%95%BD+%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98&newwindow=1&rlz=1C5CHFA_enKR940KR940&biw=2560&bih=1329&sxsrf=ALeKk030HHsjqSzJ2LvBzbpWBqfjfALSQQ%3A1621991969400&ei=IaKtYJT0F6uUr7wP166w-Ac&oq=url&gs_lcp=Cgdnd3Mtd2l6EAMYATIECCMQJzIECCMQJzIECCMQJzIECAAQQzIHCAAQsQMQQzIICAAQsQMQgwEyAggAMgIIADICCAAyCAgAELEDEIMBOgcIIxCwAxAnOgkIABCwAxAHEB46CQgAELADEAUQHjoICAAQsAMQywE6CQgAELADEAgQHjoHCCMQsAIQJzoECAAQDToICAAQCBANEB46CAgAEA0QBRAeOggIABAHEAoQHjoICAAQCBAHEB46BggAEAcQHjoFCAAQywE6CggAEAcQChAeEBM6CggAEAgQBxAeEBM6CAgAEAcQHhATOgQIABATOgUIABCxA1Cj_AdYkKgIYO-5CGgBcAB4AIABeYgBlgeSAQMwLjiYAQCgAQGqAQdnd3Mtd2l6yAEKwAEB&sclient=gws-wiz"};

  public static String[] NOTVALID_URI = new String[]{
      "https"};

  public static String[] VALID_URITOKENID = new String[]{
      String.valueOf(0),
      String.valueOf(1),
      String.valueOf(100),
      String.valueOf(Integer.MAX_VALUE),
      String.valueOf(BASE62_MAX_VALUE)};

  public static String[] NOTVALID_URITOKENID = new String[]{String.valueOf(-1)};
}

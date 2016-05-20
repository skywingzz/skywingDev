import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by skywing on 2016. 2. 1..
 */
public class test {
    public static void main(String[] args) throws Exception {
        String queryString = "test=123&bubbles=%25EC%259B%2590%25ED%2594%25BC%25EC%258A%25A4&bubbleKeywords=%25EC%259B%2590%25ED%2594%25BC%25EC%258A%25A4&searchKeyword=%25EC%259B%2590%25ED%2594%25BC%25EC%258A%25A4&decSearchKeyword=%EC%9B%90%ED%94%BC%EC%8A%A4&viewType=image";

        queryString = queryString.replaceAll("[&]*viewType=[^&]*", "");

        System.out.println(queryString);

        String url = "http://www.gsisuper.com/images/DV/st11/0229월_영남.jpg?sadf=dd";
        URL fileURL = new URL(url);

        System.out.println(URLEncoder.encode(fileURL.getFile()));


        System.out.println(filePathEncoding(url));

        URL dd = new URL(filePathEncoding(url));


        long discountRate = 0;
        long selPrc = 760000;
        long finalDscPrc = 1000;
        if (selPrc > 0 && finalDscPrc > 0) {
            discountRate = Math.round(((double)(selPrc - finalDscPrc)/(double)(selPrc) * 100));
            if(discountRate >= 100) {
                discountRate = 99;
            }
        }

        BigDecimal dSellPrice = new BigDecimal(Long.toString(selPrc));
        BigDecimal dFinalPrice = new BigDecimal(Long.toString(finalDscPrc));
        int rtnInt = 0;
        try {
            if(dSellPrice.compareTo(dFinalPrice) == 1){
                BigDecimal dscRate = (dSellPrice.subtract(dFinalPrice)).divide(dSellPrice, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
                dscRate = dscRate.setScale(0); // 소숫점 없는 포멧
                rtnInt = dscRate.intValue();

                if(rtnInt >= 100) {
                    rtnInt = 99;
                }
            }
        } catch (Exception e){}

        System.out.println(rtnInt);


    }

    public static String filePathEncoding(String url) throws Exception {
        String path = url.substring(0, url.lastIndexOf('/') + 1);
        String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
        return path + URLEncoder.encode(fileName, "UTF-8");
    }

}

package wx.milk.web.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: kiven on 2018/9/7/007 16:48
 * try it bast!
 */
public class DomainUtils {


    /*public static List<String> allCountryDomain = new ArrayList();
    public static List<String> allOrganizationDomain = new ArrayList();*/

    public DomainUtils() {
    }

    public static String getBaseDomain(String customDomain) {
        if (customDomain == null) {
            return null;
        } else {
            String[] parts = customDomain.split("\\.");
            if (parts != null && parts.length != 0) {
                StringBuffer domain = new StringBuffer();
                if (parts.length >= 3 && (parts.length != 3)) {
                    domain.append(parts[parts.length - 3]);
                    domain.append(".");
                    domain.append(parts[parts.length - 2]);
                    domain.append(".");
                    domain.append(parts[parts.length - 1]);
                } else {
                    for(int i = 0; i < parts.length; ++i) {
                        if (i != 0) {
                            domain.append(".");
                        }

                        domain.append(parts[i]);
                    }
                }

                return domain.toString();
            } else {
                return null;
            }
        }
    }

    /*public static String getBaseDomain(String customDomain) {
        if (customDomain == null) {
            return null;
        } else {
            String[] parts = customDomain.split("\\.");
            if (parts != null && parts.length != 0) {
                StringBuffer domain = new StringBuffer();
                if (parts.length >= 3 && (parts.length != 3 || !allOrganizationDomain.contains(parts[1]) || !allCountryDomain.contains(parts[2]))) {
                    if (allOrganizationDomain.contains(parts[parts.length - 2]) && allCountryDomain.contains(parts[parts.length - 1])) {
                        domain.append(parts[parts.length - 3]);
                        domain.append(".");
                        domain.append(parts[parts.length - 2]);
                        domain.append(".");
                        domain.append(parts[parts.length - 1]);
                    } else {
                        domain.append(parts[parts.length - 2]);
                        domain.append(".");
                        domain.append(parts[parts.length - 1]);
                    }
                } else {
                    for(int i = 0; i < parts.length; ++i) {
                        if (i != 0) {
                            domain.append(".");
                        }

                        domain.append(parts[i]);
                    }
                }

                return domain.toString();
            } else {
                return null;
            }
        }
    }*/

    /*static {
        allCountryDomain.add("al");
        allCountryDomain.add("dz");
        allCountryDomain.add("af");
        allCountryDomain.add("ar");
        allCountryDomain.add("ae");
        allCountryDomain.add("aw");
        allCountryDomain.add("om");
        allCountryDomain.add("az");
        allCountryDomain.add("eg");
        allCountryDomain.add("et");
        allCountryDomain.add("ie");
        allCountryDomain.add("ee");
        allCountryDomain.add("ad");
        allCountryDomain.add("ao");
        allCountryDomain.add("ai");
        allCountryDomain.add("ag");
        allCountryDomain.add("at");
        allCountryDomain.add("au");
        allCountryDomain.add("mo");
        allCountryDomain.add("bb");
        allCountryDomain.add("pg");
        allCountryDomain.add("bs");
        allCountryDomain.add("pk");
        allCountryDomain.add("py");
        allCountryDomain.add("ps");
        allCountryDomain.add("bh");
        allCountryDomain.add("pa");
        allCountryDomain.add("br");
        allCountryDomain.add("by");
        allCountryDomain.add("bm");
        allCountryDomain.add("bg");
        allCountryDomain.add("mp");
        allCountryDomain.add("bj");
        allCountryDomain.add("be");
        allCountryDomain.add("is");
        allCountryDomain.add("pr");
        allCountryDomain.add("ba");
        allCountryDomain.add("pl");
        allCountryDomain.add("bo");
        allCountryDomain.add("bz");
        allCountryDomain.add("bw");
        allCountryDomain.add("bt");
        allCountryDomain.add("bf");
        allCountryDomain.add("bi");
        allCountryDomain.add("bv");
        allCountryDomain.add("kp");
        allCountryDomain.add("gq");
        allCountryDomain.add("dk");
        allCountryDomain.add("de");
        allCountryDomain.add("tl");
        allCountryDomain.add("tp");
        allCountryDomain.add("tg");
        allCountryDomain.add("dm");
        allCountryDomain.add("do");
        allCountryDomain.add("ru");
        allCountryDomain.add("ec");
        allCountryDomain.add("er");
        allCountryDomain.add("ee");
        allCountryDomain.add("fr");
        allCountryDomain.add("fo");
        allCountryDomain.add("pf");
        allCountryDomain.add("gf");
        allCountryDomain.add("tf");
        allCountryDomain.add("va");
        allCountryDomain.add("ph");
        allCountryDomain.add("fj");
        allCountryDomain.add("fi");
        allCountryDomain.add("cv");
        allCountryDomain.add("fk");
        allCountryDomain.add("gm");
        allCountryDomain.add("cg");
        allCountryDomain.add("cd");
        allCountryDomain.add("co");
        allCountryDomain.add("cr");
        allCountryDomain.add("gg");
        allCountryDomain.add("gd");
        allCountryDomain.add("gl");
        allCountryDomain.add("ge");
        allCountryDomain.add("cu");
        allCountryDomain.add("gp");
        allCountryDomain.add("gu");
        allCountryDomain.add("gy");
        allCountryDomain.add("kz");
        allCountryDomain.add("ht");
        allCountryDomain.add("kr");
        allCountryDomain.add("nl");
        allCountryDomain.add("an");
        allCountryDomain.add("hm");
        allCountryDomain.add("hn");
        allCountryDomain.add("ki");
        allCountryDomain.add("dj");
        allCountryDomain.add("kg");
        allCountryDomain.add("gn");
        allCountryDomain.add("gw");
        allCountryDomain.add("ca");
        allCountryDomain.add("gh");
        allCountryDomain.add("ga");
        allCountryDomain.add("kh");
        allCountryDomain.add("cz");
        allCountryDomain.add("zw");
        allCountryDomain.add("cm");
        allCountryDomain.add("qa");
        allCountryDomain.add("ky");
        allCountryDomain.add("km");
        allCountryDomain.add("ci");
        allCountryDomain.add("kw");
        allCountryDomain.add("cc");
        allCountryDomain.add("hr");
        allCountryDomain.add("ke");
        allCountryDomain.add("ck");
        allCountryDomain.add("lv");
        allCountryDomain.add("ls");
        allCountryDomain.add("la");
        allCountryDomain.add("lb");
        allCountryDomain.add("lt");
        allCountryDomain.add("lr");
        allCountryDomain.add("ly");
        allCountryDomain.add("li");
        allCountryDomain.add("re");
        allCountryDomain.add("lu");
        allCountryDomain.add("rw");
        allCountryDomain.add("ro");
        allCountryDomain.add("mg");
        allCountryDomain.add("im");
        allCountryDomain.add("mv");
        allCountryDomain.add("mt");
        allCountryDomain.add("mw");
        allCountryDomain.add("my");
        allCountryDomain.add("ml");
        allCountryDomain.add("mk");
        allCountryDomain.add("mh");
        allCountryDomain.add("mq");
        allCountryDomain.add("yt");
        allCountryDomain.add("mu");
        allCountryDomain.add("mr");
        allCountryDomain.add("us");
        allCountryDomain.add("um");
        allCountryDomain.add("as");
        allCountryDomain.add("vi");
        allCountryDomain.add("mn");
        allCountryDomain.add("ms");
        allCountryDomain.add("bd");
        allCountryDomain.add("pe");
        allCountryDomain.add("fm");
        allCountryDomain.add("mm");
        allCountryDomain.add("md");
        allCountryDomain.add("ma");
        allCountryDomain.add("mc");
        allCountryDomain.add("mz");
        allCountryDomain.add("mx");
        allCountryDomain.add("na");
        allCountryDomain.add("za");
        allCountryDomain.add("aq");
        allCountryDomain.add("gs");
        allCountryDomain.add("nr");
        allCountryDomain.add("np");
        allCountryDomain.add("ni");
        allCountryDomain.add("ne");
        allCountryDomain.add("ng");
        allCountryDomain.add("nu");
        allCountryDomain.add("no");
        allCountryDomain.add("nf");
        allCountryDomain.add("pw");
        allCountryDomain.add("pn");
        allCountryDomain.add("pt");
        allCountryDomain.add("jp");
        allCountryDomain.add("se");
        allCountryDomain.add("ch");
        allCountryDomain.add("sv");
        allCountryDomain.add("ws");
        allCountryDomain.add("yu");
        allCountryDomain.add("sl");
        allCountryDomain.add("sn");
        allCountryDomain.add("cy");
        allCountryDomain.add("sc");
        allCountryDomain.add("sa");
        allCountryDomain.add("cx");
        allCountryDomain.add("st");
        allCountryDomain.add("sh");
        allCountryDomain.add("kn");
        allCountryDomain.add("lc");
        allCountryDomain.add("sm");
        allCountryDomain.add("pm");
        allCountryDomain.add("vc");
        allCountryDomain.add("lk");
        allCountryDomain.add("sk");
        allCountryDomain.add("si");
        allCountryDomain.add("sj");
        allCountryDomain.add("sz");
        allCountryDomain.add("sd");
        allCountryDomain.add("sr");
        allCountryDomain.add("sb");
        allCountryDomain.add("so");
        allCountryDomain.add("tj");
        allCountryDomain.add("tw");
        allCountryDomain.add("th");
        allCountryDomain.add("tz");
        allCountryDomain.add("to");
        allCountryDomain.add("tc");
        allCountryDomain.add("tt");
        allCountryDomain.add("tn");
        allCountryDomain.add("tv");
        allCountryDomain.add("tr");
        allCountryDomain.add("tm");
        allCountryDomain.add("tk");
        allCountryDomain.add("wf");
        allCountryDomain.add("vu");
        allCountryDomain.add("gt");
        allCountryDomain.add("ve");
        allCountryDomain.add("bn");
        allCountryDomain.add("ug");
        allCountryDomain.add("ua");
        allCountryDomain.add("uy");
        allCountryDomain.add("uz");
        allCountryDomain.add("es");
        allCountryDomain.add("eh");
        allCountryDomain.add("gr");
        allCountryDomain.add("hk");
        allCountryDomain.add("sg");
        allCountryDomain.add("nc");
        allCountryDomain.add("nz");
        allCountryDomain.add("hu");
        allCountryDomain.add("sy");
        allCountryDomain.add("jm");
        allCountryDomain.add("am");
        allCountryDomain.add("ac");
        allCountryDomain.add("ye");
        allCountryDomain.add("iq");
        allCountryDomain.add("ir");
        allCountryDomain.add("il");
        allCountryDomain.add("it");
        allCountryDomain.add("in");
        allCountryDomain.add("id");
        allCountryDomain.add("uk");
        allCountryDomain.add("vg");
        allCountryDomain.add("io");
        allCountryDomain.add("jo");
        allCountryDomain.add("vn");
        allCountryDomain.add("zm");
        allCountryDomain.add("je");
        allCountryDomain.add("td");
        allCountryDomain.add("gi");
        allCountryDomain.add("cl");
        allCountryDomain.add("cf");
        allCountryDomain.add("cn");
        allOrganizationDomain.add("edu");
        allOrganizationDomain.add("gov");
        allOrganizationDomain.add("int");
        allOrganizationDomain.add("mil");
        allOrganizationDomain.add("net");
        allOrganizationDomain.add("com");
        allOrganizationDomain.add("org");
        allOrganizationDomain.add("biz");
        allOrganizationDomain.add("info");
        allOrganizationDomain.add("pro");
        allOrganizationDomain.add("name");
        allOrganizationDomain.add("museum");
        allOrganizationDomain.add("coop");
        allOrganizationDomain.add("aero");
        allOrganizationDomain.add("xxx");
        allOrganizationDomain.add("idv");
    }*/
}

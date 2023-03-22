package com.stevin.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 金额工具类
 *
 * @author yangyi
 * @描述: 人民币 数字转大写中文格式
 * 人民币大写金额写法及注意事项
 * 银行、单位和个人填写的各种票据和结算凭证是办理支付结算和现金收付的重要依据，直接关系到支付结算的准确、及时和安全。
 * 票据和结算凭证是银行、单位和个人凭以记载账务的会计凭证，是记载经济业务和明确经济责任的一种书面证明。
 * 因此，填写票据和结算凭证必须做到标准化、规范化、要素齐全、数字正确、字迹清晰、不错漏、不潦草、防止涂改。
 * 中文大写金额数字应用正楷或行书填写，如壹、贰、叁、肆、伍、陆、柒、捌、玖、拾、佰、仟、万、亿、元、角、分、零、整（正）等字样，不得用一、二（两）、三、四、五、六、七、八、九、十、毛、另（或0）填写，不得自造简化字。如果金额数字书写中使用繁体字，如贰、陆、亿、万、圆的，也应受理。
 * 人民币大写的正确写法还应注意以下几项：
 * 一、中文大写金额数字到“元”为止的，在“元”之后、应写“整”（或“正”）字；在“角”之后，可以不写“整”（或“正”）字；大写金额数字有“分”的，“分”后面不写“整”（或“正”）字。
 * 二、中文大写金额数字前应标明“人民币”字样，大写金额数字应紧接“人民币”字样填写，不得留有空白。
 * 大写金额数字前未印“人民币”字样的，应加填“人民币”三字，在票据和结算凭证大写金额栏内不得预印固定的“仟、佰、拾、万、仟、佰、拾、元、角、分”字样。
 * 三、阿拉伯数字小写金额数字中有“0”时，中文大写应按照汉语语言规律、金额数字构成和防止涂改的要求进行书写。
 * 举例如下：
 * 1、阿拉伯数字中间有“0”时，中文大写要写“零”字，如￥1409.50应写成人民币壹仟肆佰零玖元伍角；
 * 2、阿拉伯数字中间连续有几个“0”时、中文大写金额中间可以只写一个“零”字，如￥6007.14应写成人民币陆仟零柒元壹角肆分。
 * 3、阿拉伯金额数字万位和元位是“0”，或者数字中间连续有几个“0”，万位、元位也是“0”但千位、角位不是“0”时，中文大写金额中可以只写一个零字，也可以不写“零”字.
 * 如￥1680.32应写成人民币壹仟陆佰捌拾元零叁角贰分，或者写成人民币壹仟陆佰捌拾元叁角贰分。
 * 又如￥107000.53应写成人民币壹拾万柒仟 元零伍角叁分，或者写成人民币壹拾万零柒仟元伍角叁分。
 * 4、阿拉伯金额数字角位是“0”而分位不是“0”时，中文大写金额“元”后面应写“零”字，如￥16409.02应写成人民币壹万陆仟肆佰零玖元零贰分，又如￥325.04应写成人民币叁佰贰拾伍元零肆分。
 * 四、阿拉伯小写金额数字前面均应填写人民币符号“￥”，阿拉伯小写金额数字要认真填写，不得连写分辨不清。
 * 五、票据的出票日期必须使用中文大写，为防止变造票据的出票日期，在填写月、日时、月为壹、贰和壹拾的，日为壹至玖和壹拾、贰拾和叁拾的，应在其前加“零”，日为拾壹至拾玖的应在其前加“壹”，如1月15日应写成零壹月壹拾伍日，再如10月20日应写成零壹拾月零贰拾日。
 * 六、票据出票日期使用小写填写的，银行不予受理；大写日期未按要求规范填写的，银行可予受理，但由此造成损失的由出票人自行承担。
 * @description
 * @Date 2022/10/27
 */
public class MoneyUtils {
    /**
     * 汉语中数字大写
     */
    private static final String[] CN_UPPER_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    /**
     * 汉语中货币单位大写，这样的设计类似于占位符
     */
    private static final String[] CN_UPPER_MONETARY_UNIT = {"分", "角", "元",
            "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
            "佰", "仟"};

    /**
     * 只有各单位的单独字符
     */
    private static final String[] CN_UPPER_SINGLE_MONETARY_UNIT = {"分", "角", "元", "拾", "佰", "仟", "万", "亿", "兆"};

    /**
     * 从元开始向上递增的单位占位符
     */
    private static final String[] CN_ZS_UPPER_MONETARY_UNIT = {"元",
            "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
            "佰", "仟"};

    /**
     * 关键单位
     */
    private static final String[] TARGETS = {"亿", "万", "元", "兆"};

    private static final String[] CN_ZS_UPPER_REVERSER_MONETARY_UNIT = {"佰", "拾", "元"};

    /**
     * 所有中文金额大写可能出现的字符全集
     */
    private static final String[] CN_NUMBER_ALL = {"零", "壹", "贰", "貳", "叁", "肆", "伍", "陆", "陸", "柒", "捌", "玖",
            "分", "角", "元", "圆", "圓", "拾", "佰", "仟", "万", "萬", "亿", "億", "兆",
            "整", "正"};
    /**
     * 元的多种书写情况
     */
    private static final String[] CN_YUAN = {"元", "圆", "圓"};
    /**
     * 整的多种书写情况
     */
    private static final String[] CN_Z = {"整", "正"};
    /**
     * 特殊字符：整
     */
    private static final String CN_FULL = "整";
    /**
     * 特殊字符：负
     */
    private static final String CN_NEGATIVE = "负";
    /**
     * 金额的精度，默认值为2
     */
    private static final int MONEY_PRECISION = 2;
    /**
     * 特殊字符：零元整
     */
    private static final String CN_ZEOR_FULL = "零元" + CN_FULL;
    /**
     * 四舍五入
     */
    public static final RoundingMode HALF_UP = RoundingMode.HALF_UP;
    /**
     * 向上取整
     */
    public static final RoundingMode UP = RoundingMode.UP;

    /**
     * 把输入的金额转换为汉语中人民币的大写
     *
     * @param numberStr 输入的金额
     * @return 对应的汉语大写
     */
    public static String toChineseAmount(String numberStr) {
        BigDecimal numberOfMoney = new BigDecimal(numberStr);
        StringBuffer sb = new StringBuffer();
        // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
        // positive.
        int signum = numberOfMoney.signum();
        // 零元整的情况
        if (signum == 0) {
            return CN_ZEOR_FULL;
        }
        // 这里会进行金额的四舍五入
        long number = numberOfMoney.movePointRight(MONEY_PRECISION)
                .setScale(0, 4).abs().longValue();
        // 得到小数点后两位值
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
        if (!(scale > 0)) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) {
                break;
            }
            // 每次获取到最后一个数
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETARY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETARY_UNIT[10]);
                }
                sb.insert(0, CN_UPPER_MONETARY_UNIT[numIndex]);
                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    if (number > 0) {
                        sb.insert(0, CN_UPPER_MONETARY_UNIT[numIndex]);
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, CN_UPPER_MONETARY_UNIT[numIndex]);
                }
                getZero = true;
            }
            // 让number每次都去掉最后一个数
            number = number / 10;
            ++numIndex;
        }
        // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }
        // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
        if (!(scale > 0)) {
            sb.append(CN_FULL);
        }
        return sb.toString();
    }

    /**
     * 大写金额转数字
     *
     * @param chineseAmount
     * @return
     * @throws Exception
     */
    public static String chineseConvertToNumber(String chineseAmount) {
        if (StrUtil.isEmpty(chineseAmount)) {
            return null;
        }
        //移除计算干扰文字
        chineseAmount = chineseAmount
                .replace(CN_YUAN[0], "")
                .replace(CN_YUAN[1], "")
                .replace(CN_YUAN[2], "")
                .replace(CN_Z[0], "")
                .replace(CN_Z[1], "");

        // 字符切割
        char[] wordCharArray = chineseAmount.toCharArray();

        //最终要返回的数字金额
        BigDecimal numberAmount = BigDecimal.ZERO;

        //金额位标志量
        //表示有分位
        boolean fen = false;
        //表示有角位
        boolean jiao = false;
        // 表示个位不为0
        boolean yuan = false;
        //表示有十位
        boolean shi = false;
        //表示有百位
        boolean bai = false;
        //表示有千位
        boolean qian = false;
        //表示有万位
        boolean wan = false;
        //表示有亿位
        boolean yi = false;

        //从低位到高位计算
        for (int i = (wordCharArray.length - 1); i >= 0; i--) {
            //当前位金额值
            BigDecimal currentPlaceAmount = BigDecimal.ZERO;

            //判断当前位对应金额标志量
            if (wordCharArray[i] == '分') {
                fen = true;
                continue;
            } else if (wordCharArray[i] == '角') {
                jiao = true;
                continue;
            } else if (wordCharArray[i] == '元' || wordCharArray[i] == '圆' || wordCharArray[i] == '圓') {
                yuan = true;
                continue;
            } else if (wordCharArray[i] == '拾') {
                shi = true;
                continue;
            } else if (wordCharArray[i] == '佰') {
                bai = true;
                continue;
            } else if (wordCharArray[i] == '仟') {
                qian = true;
                continue;
            } else if (wordCharArray[i] == '万' || wordCharArray[i] == '萬') {
                wan = true;
                continue;
            } else if (wordCharArray[i] == '亿' || wordCharArray[i] == '億') {
                yi = true;
                continue;
            }

            //根据标志量转换金额为实际金额
            double t = 0;
            if (fen) {
                t = ConvertNameToSmall(wordCharArray[i]) * 0.01;
            } else if (jiao) {
                t = ConvertNameToSmall(wordCharArray[i]) * 0.1;
            } else if (shi) {
                t = ConvertNameToSmall(wordCharArray[i]) * 10;
            } else if (bai) {
                t = ConvertNameToSmall(wordCharArray[i]) * 100;
            } else if (qian) {
                t = ConvertNameToSmall(wordCharArray[i]) * 1000;
            } else {
                t = ConvertNameToSmall(wordCharArray[i]);
            }
            currentPlaceAmount = new BigDecimal(t);
            //每万位处理
            if (yi) {
                currentPlaceAmount = currentPlaceAmount.multiply(new BigDecimal(100000000));
            } else if (wan) {
                currentPlaceAmount = currentPlaceAmount.multiply(new BigDecimal(10000));
            }
            numberAmount = numberAmount.add(currentPlaceAmount);
            // 重置状态
            //yi = false; // 亿和万  不可重置 下次循环还会用到
            //wan = false;
            fen = false;
            jiao = false;
            shi = false;
            bai = false;
            qian = false;
            yuan = false;
        }
        return numberAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 检查大写金额规则
     *
     * @param chineseAmount
     * @return
     */
    public static boolean checkAmountRules(String chineseAmount) {
        //大写金额数字有“分”的，“分”后面不写“整”（或“正”）字。
        if (StrUtil.isEmpty(chineseAmount)) {
            return false;
        }
        //如果出现了任意一个不在CN_NUMBER_ALL中的字符，则返回false
        String[] wordCharArray = chineseAmount.split("");
        for (String c : wordCharArray) {
            if (!ArrayUtil.contains(CN_NUMBER_ALL, c)) {
                return false;
            }
        }
        //最后一个字符
        String lastChar = chineseAmount.substring(chineseAmount.length() - 1);
        boolean containYuan = chineseAmount.contains(CN_YUAN[0])
                || chineseAmount.contains(CN_YUAN[1])
                || chineseAmount.contains(CN_YUAN[2]);
        //中文大写金额数字到“元”为止的，在“元”之后、应写“整”（或“正”）字；
        if (ArrayUtil.contains(CN_YUAN, lastChar)) {
            return false;
        }
        if (chineseAmount.contains(CN_UPPER_MONETARY_UNIT[0]) && ArrayUtil.contains(CN_Z, lastChar)) {
            return false;
        }
        //阿拉伯金额数字角位是“0”而分位不是“0”时，中文大写金额“元”后面应写“零”字
        if (chineseAmount.endsWith(CN_UPPER_MONETARY_UNIT[0])) {
            String chineseNumber = getChineseNumberWithEx(chineseAmount);
            if (chineseNumber == null) return false;
            if (StrUtil.isEmpty(chineseNumber)) {
                return false;
            }
            //如果"角"位是0
            boolean jiaoIsZero = '0' == chineseNumber.charAt(chineseNumber.length() - 2);
            boolean fenIsZero = '0' == chineseNumber.charAt(chineseNumber.length() - 1);
            boolean yuanThenZero = false;
            //如果元后面是零置为true
            if ('零' == chineseAmount.charAt(chineseAmount.lastIndexOf(CN_YUAN[0]) + 1)
                    || '零' == chineseAmount.charAt(chineseAmount.lastIndexOf(CN_YUAN[1]) + 1)
                    || '零' == chineseAmount.charAt(chineseAmount.lastIndexOf(CN_YUAN[2]) + 1)) {
                yuanThenZero = true;
            }
            if (jiaoIsZero && !fenIsZero && containYuan && !yuanThenZero) {
                return false;
            }
            if (jiaoIsZero && !fenIsZero && chineseAmount.contains("零角")) {
                return false;
            }
        }
        if (containYuan) {
            String chineseNumber = getChineseNumberWithEx(chineseAmount);
            if (chineseNumber == null) return false;
            if (StrUtil.isEmpty(chineseNumber)) {
                return false;
            }
            chineseAmount = replaceComplexChinese(chineseAmount);
            //如果包含万
            if (chineseNumber.contains(".")) {
                String zsNumberStr = chineseNumber.substring(0, chineseNumber.indexOf("."));
                if (zsNumberStr.length() > 5) {
                    //拿到整数Number
                    long zsNumber = Long.valueOf(zsNumberStr);
                    int thousandUnit = (int) ((zsNumber / 1000) % 10);
                    int wanUnit = (int) ((zsNumber / 10000) % 10);
                    //万位是0，千位也是0，需要写"零"
                    if (wanUnit == 0 && thousandUnit == 0) {
                        int wanIndex = getWanIndex(chineseAmount);
                        //从万往下循环，找到不是0的那一位的单位，然后找万和这个单位之间有没有"零"
                        String numUnit = null;
                        int numIndex = 0;
                        String leftNumStr = zsNumberStr.substring(zsNumberStr.length() - 3);
                        while (true) {
                            if (leftNumStr.length() <= 0) {
                                break;
                            }
                            //从佰开始去掉最前面的字符
                            numUnit = leftNumStr.substring(0, 1);
                            if ("0".equals(numUnit) || numIndex == 0) {
                                //让zsNumberStr每次都去掉最前面的一位数
                                leftNumStr = leftNumStr.substring(1);
                                ++numIndex;
                                continue;
                            } else {
                                //查看所在位置
//                                chineseAmount = replaceComplexChinese(chineseAmount);
                                String unit = CN_ZS_UPPER_REVERSER_MONETARY_UNIT[numIndex];
                                //拿到第一个不是零的靠近万的字符串
                                String wanToUnitStr = chineseAmount.substring(chineseAmount.lastIndexOf(CN_NUMBER_ALL[wanIndex]),
                                        chineseAmount.lastIndexOf(unit));
                                if (!wanToUnitStr.contains(CN_NUMBER_ALL[0])) {
                                    return false;
                                }
                            }
                            leftNumStr = leftNumStr.substring(1);
                            ++numIndex;
                        }
                    }
                }
            }
            //元位是否为零，为零则从CN_UPPER_MONETARY_UNIT[3]开始，查找ChineseAmount中是否有，如果匹配到，则中间写零返回false
            boolean yuanIsZero = "0".equals(chineseNumber.substring(chineseNumber.indexOf(".") - 1, chineseNumber.indexOf(".")));
            if (yuanIsZero) {
                if ("0.00".equals(chineseNumber)) {
                    return true;
                }
                chineseAmount = replaceComplexChinese(chineseAmount);
                int yuanAfterReplaceIndex = getYuanIndex(chineseAmount);
                //元位是0，从拾到兆，找到第一个CN_UPPER_MONETARY_UNIT碰到的单位，这个单位和元之间不能有零
                String[] splitAmount = chineseAmount.split("元")[0].split("");
                String yuanBeforeUnit = "元";
                //找到元前面的单位
                for (int j = splitAmount.length - 1; j > 0; j--) {
                    for (int i = 3; i < CN_UPPER_SINGLE_MONETARY_UNIT.length; i++) {
                        if (CN_UPPER_SINGLE_MONETARY_UNIT[i].equals(splitAmount[j])) {
                            yuanBeforeUnit = splitAmount[j];
                            break;
                        }
                    }
                    if (!"元".equals(yuanBeforeUnit)) {
                        break;
                    }
                }
                String judgeParagraph = chineseAmount.substring(chineseAmount.lastIndexOf(yuanBeforeUnit),
                        chineseAmount.lastIndexOf(CN_NUMBER_ALL[yuanAfterReplaceIndex]));
                if (judgeParagraph.contains(CN_NUMBER_ALL[0])) {
                    return false;
                }
            } else {
                //元位不是零，两个数字间至少写一个零，查找原数字中是否有0出现，有的话查看位数在哪里
                //100204.05--壹拾万零贰佰零肆元零伍分，百位及以上是否有0，有则找到最后一个不是0的数字的单位，查找此单位和元之前是否有零，没有则false
                String zsNumberStr = chineseNumber.substring(0, chineseNumber.indexOf("."));
                //拿到整数Number
                long zsNumber = Long.valueOf(zsNumberStr);
                //万位是0千位不是0，可以不写"零"
                boolean wanZeroThousandNotZero = judgeWanAndThousand(zsNumber);
                int numUnit = 0;
                int numIndex = 0;
                while (true) {
                    if (zsNumber <= 0) {
                        break;
                    }
                    numUnit = (int) (zsNumber % 10);
                    if (numUnit == 0 || numIndex == 0) {
                        // 让number每次都去掉最后一个数
                        zsNumber = zsNumber / 10;
                        ++numIndex;
                        continue;
                    } else {
                        //查看所在位置
                        chineseAmount = replaceComplexChinese(chineseAmount);
//                        int yuanAfterReplaceIndex = getYuanIndex(chineseAmount);
                        String unit = CN_ZS_UPPER_MONETARY_UNIT[numIndex];
                        //确定要取的区间，确定基准单位兆、亿、万、元
                        String nearUnitByIndex = getNearUnitByIndex(numIndex);
                        //拿到第一个不是零的靠近基准单位的字符串
                        StringBuilder chAmountSb = new StringBuilder(chineseAmount);
                        String reverseChAmount = chAmountSb.reverse().toString();
                        String reverseZsFirst2StandardUnit = null;
                        //截取子字符串，加上unit的长度
                        String standard2LastStr = reverseChAmount.substring(reverseChAmount.indexOf(nearUnitByIndex));
                        reverseZsFirst2StandardUnit = standard2LastStr.substring(0, standard2LastStr.indexOf(unit) + 1);
                        StringBuilder zsSb = new StringBuilder(reverseZsFirst2StandardUnit);
                        String zsFirst2StandardUnit = zsSb.reverse().toString();
                        String zsNumberSubStr = String.valueOf(zsNumber);
                        String middleStr = zsNumberStr.substring(zsNumberStr.indexOf(zsNumberSubStr) + zsNumberSubStr.length(), zsNumberStr.length() - 1);
                        //如果middleStr包含的0是万位的0，同时整个字符串中万位是0但千位不是0的，则需要跳过去
                        boolean zeroAtWan = judgeZeroAtWan(middleStr);
                        if (!"拾".equals(unit) && middleStr.contains("0") && !zsFirst2StandardUnit.contains(CN_NUMBER_ALL[0]) && !(zeroAtWan && wanZeroThousandNotZero)) {
                            return false;
                        }
                    }
                    zsNumber = zsNumber / 10;
                    ++numIndex;
                }
            }
        }
        return true;
    }

    /**
     * 金额乘法
     *
     * @param first
     * @param second
     * @param roundingMode 默认四舍五入
     * @param scale        默认2位小数
     * @return
     * @return BigDecimal
     * @deccription 金额乘法
     */
    public static BigDecimal multiply(BigDecimal first, BigDecimal second, RoundingMode roundingMode,
                                      Integer scale) {
        if (roundingMode == null) {
            roundingMode = HALF_UP;
        }
        if (first == null)
            first = BigDecimal.ZERO;
        if (second == null)
            second = BigDecimal.ZERO;
        if (scale == null)
            scale = 2;
        return (first.multiply(second)).setScale(scale, roundingMode);
    }

    /**
     * 金额除法
     *
     * @param first
     * @param second
     * @param roundingMode 默认四舍五入
     * @param scale        默认2位小数
     * @return
     * @return BigDecimal
     * @deccription 金额除法
     */
    public static BigDecimal divide(BigDecimal first, BigDecimal second, RoundingMode roundingMode,
                                    Integer scale) {
        if (roundingMode == null) {
            roundingMode = HALF_UP;
        }
        if (first == null)
            first = BigDecimal.ZERO;
        if (second == null)
            second = BigDecimal.ZERO;
        if (scale == null)
            scale = 2;
        return first.divide(second, scale, roundingMode);
    }

    /**
     * 金额减法
     *
     * @param first
     * @param second
     * @param roundingMode 默认四舍五入
     * @param scale        默认2位小数
     * @return
     * @return BigDecimal
     * @deccription 金额减法
     */
    public static BigDecimal subtract(BigDecimal first, BigDecimal second, RoundingMode roundingMode,
                                      Integer scale) {
        if (roundingMode == null) {
            roundingMode = HALF_UP;
        }
        if (first == null)
            first = BigDecimal.ZERO;
        if (second == null)
            second = BigDecimal.ZERO;
        if (scale == null)
            scale = 2;
        return (first.subtract(second)).setScale(scale, roundingMode);
    }

    /**
     * 金额加法
     *
     * @param first
     * @param second
     * @param roundingMode 默认四舍五入
     * @param scale        默认2位小数
     * @return
     * @return BigDecimal
     * @deccription 金额加法
     */
    public static BigDecimal add(BigDecimal first, BigDecimal second, RoundingMode roundingMode, Integer scale) {
        if (roundingMode == null) {
            roundingMode = HALF_UP;
        }
        if (first == null)
            first = BigDecimal.ZERO;
        if (second == null)
            second = BigDecimal.ZERO;
        if (scale == null)
            scale = 2;
        return (first.add(second)).setScale(scale, roundingMode);
    }

    /**
     * 转换中文数字为阿拉伯数字
     *
     * @param chinese
     * @return
     * @throws Exception
     */
    private static int ConvertNameToSmall(char chinese) {
        int number = 0;
        String s = String.valueOf(chinese);
        switch (s) {
            case "零":
                number = 0;
                break;
            case "壹":
                number = 1;
                break;
            case "貳":
            case "贰":
                number = 2;
                break;
            case "叁":
                number = 3;
                break;
            case "肆":
                number = 4;
                break;
            case "伍":
                number = 5;
                break;
            case "陆":
            case "陸":
                number = 6;
                break;
            case "柒":
                number = 7;
                break;
            case "捌":
                number = 8;
                break;
            case "玖":
                number = 9;
                break;
        }
        return number;
    }

    /**
     * 判断middleStr的0是否在万位上
     *
     * @param middleStr
     * @return
     */
    private static boolean judgeZeroAtWan(String middleStr) {
        if (StrUtil.isEmpty(middleStr)) {
            return false;
        }
        StringBuilder sb = new StringBuilder(middleStr);
        sb.reverse();
        String reverseStr = sb.toString();
        if (reverseStr.length() > 4 && '0' == reverseStr.charAt(3)) {
            return true;
        }
        return false;
    }

    /**
     * 判断万位是0但千位不是0
     *
     * @param zsNumber
     * @return
     */
    private static boolean judgeWanAndThousand(long zsNumber) {
        if (zsNumber < 10000) {
            return false;
        }
        String zsNumberStr = String.valueOf(zsNumber);
        char wanWei = zsNumberStr.charAt(zsNumberStr.length() - 5);
        char qianWei = zsNumberStr.charAt(zsNumberStr.length() - 4);
        if (wanWei == '0' && qianWei != '0') {
            return true;
        }
        return false;
    }

    /**
     * 获取万在数组中的下标
     *
     * @param chineseAmount
     * @return
     */
    private static int getWanIndex(String chineseAmount) {
        int wanIndex = ArrayUtil.indexOfIgnoreCase(CN_NUMBER_ALL, "万");
        if (chineseAmount.contains("万")) {
            wanIndex = ArrayUtil.indexOfIgnoreCase(CN_NUMBER_ALL, "万");
        } else if (chineseAmount.contains("萬")) {
            wanIndex = ArrayUtil.indexOfIgnoreCase(CN_NUMBER_ALL, "萬");
        }
        return wanIndex;
    }

    /**
     * 获取元在数组中的下标
     *
     * @param chineseAmount
     * @return
     */
    private static int getYuanIndex(String chineseAmount) {
        int yuanIndex = ArrayUtil.indexOfIgnoreCase(CN_NUMBER_ALL, "元");
        if (chineseAmount.contains("元")) {
            yuanIndex = ArrayUtil.indexOfIgnoreCase(CN_NUMBER_ALL, "元");
        } else if (chineseAmount.contains("圆")) {
            yuanIndex = ArrayUtil.indexOfIgnoreCase(CN_NUMBER_ALL, "圆");
        } else if (chineseAmount.contains("圓")) {
            yuanIndex = ArrayUtil.indexOfIgnoreCase(CN_NUMBER_ALL, "圓");
        }
        return yuanIndex;
    }

    /**
     * 替换繁体中文中的字
     *
     * @param chineseAmount
     * @return
     */
    private static String replaceComplexChinese(String chineseAmount) {
        return chineseAmount
                .replaceAll("圆", "元")
                .replaceAll("圓", "元")
                .replaceAll("萬", "万")
                .replaceAll("億", "亿")
                .replaceAll("陸", "陆")
                .replaceAll("貳", "贰");
    }

    /**
     * 获取金额小写，带异常抛出
     *
     * @param chineseAmount
     * @return
     */
    private static String getChineseNumberWithEx(String chineseAmount) {
        String chineseNumber = null;
        try {
            chineseNumber = chineseConvertToNumber(chineseAmount);
        } catch (Exception e) {
            return null;
        }
        return chineseNumber;
    }

    /**
     * 找到离给定下标最近的“兆”，“亿”，“万”，“元”的字符串
     *
     * @param index
     * @return
     */
    private static String getNearUnitByIndex(int index) {
        //将数组转换为列表，并截取从开头到下标的部分
        List<String> list = Arrays.asList(CN_ZS_UPPER_MONETARY_UNIT).subList(0, index + 1);

        //使用流API过滤出目标字符串，并返回第一个匹配的元素（如果存在）
        Optional<String> result = list.stream()
                .filter(s -> Arrays.asList(TARGETS).contains(s)) //过滤条件
                .reduce((a, b) -> b); //返回最后一个匹配的元素
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

//    public static void main(String[] args) {
//        //需求（3）false
//        System.out.println(checkAmountRules("壹拾万零柒拾元零角叁分"));
//        System.out.println(checkAmountRules("壹拾万柒拾元零叁分"));
//        System.out.println(checkAmountRules("壹拾万零柒拾元叁分"));
//        //需求（4）false
//        System.out.println(checkAmountRules("叁佰伍拾万零肆仟玖拾陆元肆角叁分"));
//        System.out.println(checkAmountRules("叁佰伍拾万肆仟玖拾陆元肆角叁分"));
//        //true
//        System.out.println(checkAmountRules("壹拾萬零貳拾元零叁分"));
//        //true
//        System.out.println(checkAmountRules("壹拾萬零貳仟元零叁分"));
//        System.out.println(checkAmountRules("壹拾萬零柒拾元零叁分"));
//        System.out.println(checkAmountRules("壹億元零叁分"));
//        System.out.println(checkAmountRules("陸元零叁分"));
//        //true
//        System.out.println(checkAmountRules("壹拾万零柒拾元零叁分"));
//        System.out.println(checkAmountRules("叁分"));
//        System.out.println(checkAmountRules("貳角"));
//        System.out.println(checkAmountRules("貳角正"));
//        System.out.println(checkAmountRules("陆元整"));
//        System.out.println(checkAmountRules("陸億叁仟萬元正"));
//        System.out.println(checkAmountRules("陸億叁仟萬元正"));
//        System.out.println(checkAmountRules("陸億叁仟萬元正"));
//        System.out.println(checkAmountRules("壹拾万零贰佰零肆元零伍分"));
//        System.out.println("==========================");
//        //true
//        System.out.println(checkAmountRules("壹仟陆佰捌拾元零叁角贰分"));
//        System.out.println(checkAmountRules("壹仟陆佰捌拾元叁角贰分"));
//        //true 107000.53
//        System.out.println(checkAmountRules("壹拾万柒仟元零伍角叁分"));
//        System.out.println(checkAmountRules("壹拾万零柒仟元伍角叁分"));
//        //true
//        System.out.println(checkAmountRules("壹拾万零柒拾元零叁分"));
//        //true 3504096.43
//        System.out.println(checkAmountRules("叁佰伍拾万零肆仟零玖拾陆元肆角叁分"));
//        System.out.println(checkAmountRules("叁佰伍拾万肆仟零玖拾陆元肆角叁分"));
//        // true
//        System.out.println(checkAmountRules("壹万叁仟伍佰伍拾肆元壹角陆分"));
//        // true 7301687.36
//        System.out.println(checkAmountRules("柒佰叁拾万零壹仟陆佰捌拾柒元叁角陆分"));
//        System.out.println(checkAmountRules("柒佰叁拾万壹仟陆佰捌拾柒元叁角陆分"));
//        //true
//        System.out.println(checkAmountRules("壹仟零贰拾叁万玖仟叁佰陆拾陆元陆角柒分"));
//    }
}

package com.smallchill.core.toolbox.kit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.core.toolbox.support.FieldValidator;

/**
 * 正则相关工具类
 * 
 * @author xiaoleilu
 */
public class ReKit {
	
	/** 正则表达式匹配中文 */
	public final static String RE_CHINESE = "[\u4E00-\u9FFF]";
	
	/** 分组 */
	public final static Pattern GROUP_VAR =  Pattern.compile("\\$(\\d+)");
	
	/** 正则中需要被转义的关键字 */
	public final static Set<Character> RE_KEYS = CollectionKit.newHashSet(new Character[]{'$', '(', ')', '*', '+', '.', '[', ']', '?', '\\', '^', '{', '}', '|'});
	
	/**
	 * 常用正则表达式：匹配非负整数（正整数 + 0）
	 */
	public final static String regExp_integer_1 = "^\\d+$";

	/**
	 * 常用正则表达式：匹配正整数
	 */
	public final static String regExp_integer_2 = "^[0-9]*[1-9][0-9]*$";

	/**
	 * 常用正则表达式：匹配非正整数（负整数 + 0）
	 */
	public final static String regExp_integer_3 = "^((-\\d+) ?(0+))$";

	/**
	 * 常用正则表达式：匹配负整数
	 */
	public final static String regExp_integer_4 = "^-[0-9]*[1-9][0-9]*$";

	/**
	 * 常用正则表达式：匹配整数
	 */
	public final static String regExp_integer_5 = "^-?\\d+$";

	/**
	 * 常用正则表达式：匹配非负浮点数（正浮点数 + 0）
	 */
	public final static String regExp_float_1 = "^\\d+(\\.\\d+)?$";

	/**
	 * 常用正则表达式：匹配正浮点数
	 */
	public final static String regExp_float_2 = "^(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*))$";

	/**
	 * 常用正则表达式：匹配非正浮点数（负浮点数 + 0）
	 */
	public final static String regExp_float_3 = "^((-\\d+(\\.\\d+)?) ?(0+(\\.0+)?))$";

	/**
	 * 常用正则表达式：匹配负浮点数
	 */
	public final static String regExp_float_4 = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*)))$";

	/**
	 * 常用正则表达式：匹配浮点数
	 */
	public final static String regExp_float_5 = "^(-?\\d+)(\\.\\d+)?$";

	/**
	 * 常用正则表达式：匹配由26个英文字母组成的字符串
	 */
	public final static String regExp_letter_1 = "^[A-Za-z]+$";

	/**
	 * 常用正则表达式：匹配由26个英文字母的大写组成的字符串
	 */
	public final static String regExp_letter_2 = "^[A-Z]+$";

	/**
	 * 常用正则表达式：匹配由26个英文字母的小写组成的字符串
	 */
	public final static String regExp_letter_3 = "^[a-z]+$";

	/**
	 * 常用正则表达式：匹配由数字和26个英文字母组成的字符串
	 */
	public final static String regExp_letter_4 = "^[A-Za-z0-9]+$";

	/**
	 * 常用正则表达式：匹配由数字、26个英文字母或者下划线组成的字符串
	 */
	public final static String regExp_letter_5 = "^\\w+$";

	/**
	 * 常用正则表达式：匹配email地址
	 */
	public final static String regExp_email = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";

	/**
	 * 常用正则表达式：匹配url
	 */
	public final static String regExp_url_1 = "^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$";

	/**
	 * 常用正则表达式：匹配url
	 */
	public final static String regExp_url_2 = "[a-zA-z]+://[^\\s]*";

	/**
	 * 常用正则表达式：匹配中文字符
	 */
	public final static String regExp_chinese_1 = "[\\u4e00-\\u9fa5]";

	/**
	 * 常用正则表达式：匹配双字节字符(包括汉字在内)
	 */
	public final static String regExp_chinese_2 = "[^\\x00-\\xff]";

	/**
	 * 常用正则表达式：匹配空行
	 */
	public final static String regExp_line = "\\n[\\s ? ]*\\r";

	/**
	 * 常用正则表达式：匹配HTML标记
	 */
	public final static String regExp_html_1 = "/ <(.*)>.* <\\/\\1> ? <(.*) \\/>/";

	/**
	 * 常用正则表达式：匹配首尾空格
	 */
	public final static String regExp_startEndEmpty = "(^\\s*) ?(\\s*$)";

	/**
	 * 常用正则表达式：匹配帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)
	 */
	public final static String regExp_accountNumber = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";

	/**
	 * 常用正则表达式：匹配国内电话号码，匹配形式如 0511-4405222 或 021-87888822
	 */
	public final static String regExp_telephone = "\\d{3}-\\d{8} ?\\d{4}-\\d{7}";

	/**
	 * 常用正则表达式：腾讯QQ号, 腾讯QQ号从10000开始
	 */
	public final static String regExp_qq = "[1-9][0-9]{4,}";

	/**
	 * 常用正则表达式：匹配中国邮政编码
	 */
	public final static String regExp_postbody = "[1-9]\\d{5}(?!\\d)";

	/**
	 * 常用正则表达式：匹配身份证, 中国的身份证为15位或18位
	 */
	public final static String regExp_idCard = "\\d{15} ?\\d{18}";

	/**
	 * 常用正则表达式：IP
	 */
	public final static String regExp_ip = "\\d+\\.\\d+\\.\\d+\\.\\d+";
	
	private ReKit() {
		//阻止实例化
	}

	/**
	 * 获得匹配的字符串
	 * 
	 * @param regex 匹配的正则
	 * @param content 被匹配的内容
	 * @param groupIndex 匹配正则的分组序号
	 * @return 匹配后得到的字符串，未匹配返回null
	 */
	public static String get(String regex, String content, int groupIndex) {
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		return get(pattern, content, groupIndex);
	}
	
	/**
	 * 获得匹配的字符串
	 * 
	 * @param pattern 编译后的正则模式
	 * @param content 被匹配的内容
	 * @param groupIndex 匹配正则的分组序号
	 * @return 匹配后得到的字符串，未匹配返回null
	 */
	public static String get(Pattern pattern, String content, int groupIndex) {
		if(null == content){
			return null;
		}
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			return matcher.group(groupIndex);
		}
		return null;
	}
	
	/**
	 * 从content中匹配出多个值并根据template生成新的字符串<br>
	 * 例如：<br>
	 * 		content		2013年5月
	 * 		pattern			(.*?})年(.*?)月
	 * 		template：	$1-$2
	 * 		return 			2013-5
	 * 
	 * @param pattern 匹配正则
	 * @param content 被匹配的内容
	 * @param template 生成内容模板，变量 $1 表示group1的内容，以此类推
	 * @return 新字符串
	 */
	public static String extractMulti(Pattern pattern, String content, String template) {
		HashSet<String> varNums = findAll(GROUP_VAR, template, 1, new HashSet<String>());
		
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			for (String var : varNums) {
				int group = Integer.parseInt(var);
				template = template.replace("$" + var, matcher.group(group));
			}
			return template;
		}
		return null;
	}
	
	/**
	 * 从content中匹配出多个值并根据template生成新的字符串<br>
	 * 匹配结束后会删除匹配内容之前的内容（包括匹配内容）<br>
	 * 例如：<br>
	 * 		content		2013年5月
	 * 		pattern			(.*?})年(.*?)月
	 * 		template：	$1-$2
	 * 		return 			2013-5
	 * 
	 * @param pattern 匹配正则
	 * @param contents 被匹配的内容，数组0为内容正文
	 * @param template 生成内容模板，变量 $1 表示group1的内容，以此类推
	 * @return 新字符串
	 */
	public static String extractMultiAndDelPre(Pattern pattern, String[] contents, String template) {
		HashSet<String> varNums = findAll(GROUP_VAR, template, 1, new HashSet<String>());
		
		final String content = contents[0];
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			for (String var : varNums) {
				int group = Integer.parseInt(var);
				template = template.replace("$" + var, matcher.group(group));
			}
			contents[0] = StrKit.sub(content, matcher.end(), content.length());
			return template;
		}
		return null;
	}
	
	/**
	 * 从content中匹配出多个值并根据template生成新的字符串<br>
	 * 匹配结束后会删除匹配内容之前的内容（包括匹配内容）<br>
	 * 例如：<br>
	 * 		content		2013年5月
	 * 		pattern			(.*?})年(.*?)月
	 * 		template：	$1-$2
	 * 		return 			2013-5
	 * 
	 * @param regex 匹配正则字符串
	 * @param content 被匹配的内容
	 * @param template 生成内容模板，变量 $1 表示group1的内容，以此类推
	 * @return 按照template拼接后的字符串
	 */
	public static String extractMulti(String regex, String content, String template) {
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		return extractMulti(pattern, content, template);
	}
	
	/**
	 * 从content中匹配出多个值并根据template生成新的字符串<br>
	 * 例如：<br>
	 * 		content		2013年5月
	 * 		pattern			(.*?})年(.*?)月
	 * 		template：	$1-$2
	 * 		return 			2013-5
	 * 
	 * @param regex 匹配正则字符串
	 * @param contents 被匹配的内容
	 * @param template 生成内容模板，变量 $1 表示group1的内容，以此类推
	 * @return 按照template拼接后的字符串
	 */
	public static String extractMultiAndDelPre(String regex, String[] contents, String template) {
		final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		return extractMultiAndDelPre(pattern, contents, template);
	}

	/**
	 * 删除匹配的内容
	 * 
	 * @param regex 正则
	 * @param content 被匹配的内容
	 * @return 删除后剩余的内容
	 */
	public static String delFirst(String regex, String content) {
		return content.replaceFirst(regex, "");
	}

	/**
	 * 删除正则匹配到的内容之前的字符 如果没有找到，则返回原文
	 * 
	 * @param regex 定位正则
	 * @param content 被查找的内容
	 * @return 删除前缀后的新内容
	 */
	public static String delPre(String regex, String content) {
		Matcher matcher = Pattern.compile(regex, Pattern.DOTALL).matcher(content);
		if (matcher.find()) {
			return StrKit.sub(content, matcher.end(), content.length());
		}
		return content;
	}

	/**
	 * 取得内容中匹配的所有结果
	 * 
	 * @param regex 正则
	 * @param content 被查找的内容
	 * @param group 正则的分组
	 * @param collection 返回的集合类型
	 * @return 结果集
	 */
	public static <T extends Collection<String>> T findAll(String regex, String content, int group, T collection) {
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		return findAll(pattern, content, group, collection);
	}
	
	/**
	 * 取得内容中匹配的所有结果
	 * 
	 * @param pattern 编译后的正则模式
	 * @param content 被查找的内容
	 * @param group 正则的分组
	 * @param collection 返回的集合类型
	 * @return 结果集
	 */
	public static <T extends Collection<String>> T findAll(Pattern pattern, String content, int group, T collection) {
		Matcher matcher = pattern.matcher(content);
		while(matcher.find()){
			collection.add(matcher.group(group));
		}
		return collection;
	}

	/**
	 * 从字符串中获得第一个整数
	 * 
	 * @param StringWithNumber 带数字的字符串
	 * @return 整数
	 */
	public static Integer getFirstNumber(String StringWithNumber) {
		return Convert.toInt(get(FieldValidator.NUMBER, StringWithNumber, 0), null);
	}
	
	/**
	 * 给定内容是否匹配正则
	 * @param regex 正则
	 * @param content 内容
	 * @return 正则为null或者""则不检查，返回true，内容为null返回false
	 */
	public static boolean isMatch(String regex, String content) {
		if(content == null) {
			//提供null的字符串为不匹配
			return false;
		}
		
		if(StrKit.isBlank(regex)) {
			//正则不存在则为全匹配
			return true;
		}
		
		return Pattern.matches(regex, content);
	}
	
	/**
	 * 给定内容是否匹配正则
	 * @param pattern 模式  
	 * @param content 内容
	 * @return 正则为null或者""则不检查，返回true，内容为null返回false
	 */
	public static boolean isMatch(Pattern pattern, String content) {
		if(content == null || pattern == null) {
			//提供null的字符串为不匹配
			return false;
		}
		return pattern.matcher(content).matches();
	}
	
	/**
	 * 正则替换指定值<br>
	 * 通过正则查找到字符串，然后把匹配到的字符串加入到replacementTemplate中，$1表示分组1的字符串
	 * @param content 文本
	 * @param regex 正则
	 * @param replacementTemplate 替换的文本模板，可以使用$1类似的变量提取正则匹配出的内容
	 * @return 处理后的文本
	 */
	public static String replaceAll(String content, String regex, String replacementTemplate) {
		if(StrKit.isBlank(content)){
			return content;
		}
		
		final Matcher matcher = Pattern.compile(regex, Pattern.DOTALL).matcher(content);
		matcher.reset();
		boolean result = matcher.find();
		if (result) {
			final Set<String> varNums = findAll(GROUP_VAR, replacementTemplate, 1, new HashSet<String>());
			final StringBuffer sb = new StringBuffer();
			do {
				String replacement = replacementTemplate;
				for (String var : varNums) {
					int group = Integer.parseInt(var);
					replacement = replacement.replace("$" + var, matcher.group(group));
				}
				matcher.appendReplacement(sb, escape(replacement));
				result = matcher.find();
			} while (result);
			matcher.appendTail(sb);
			return sb.toString();
		}
		return content;
	}
	
	/**
	 * 转义字符串，将正则的关键字转义
	 * @param content 文本
	 * @return 转义后的文本
	 */
	public static String escape(String content) {
		if(StrKit.isBlank(content)){
			return content;
		}
		
		final StringBuilder builder = new StringBuilder();
		char current;
		for(int i = 0; i < content.length(); i++) {
			current = content.charAt(i);
			if(RE_KEYS.contains(current)) {
				builder.append('\\');
			}
			builder.append(current);
		}
		return builder.toString();
	}
	
	/**
	 * 是否匹配到结果
	 * 
	 * @param regex
	 *            正则表达式
	 * @param str
	 *            目标字符串
	 * @return
	 */
	public static boolean isExist(String regex, String str) {
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(str);
		return mat.find();
	}

	/**
	 * 正则替换
	 * 
	 * @param regex
	 *            正则表达式
	 * @param ment
	 *            替换内容
	 * @param str
	 *            目标字符串
	 * @param flags
	 *            匹配模式 Pattern.CASE_INSENSITIVE忽略大小写
	 * @return 替换后字符串
	 */
	public static String replaceAll(String regex, String ment, String str,
			int flags) {
		Pattern pat = null;
		if (flags == -1) {
			pat = Pattern.compile(regex);
		} else {
			pat = Pattern.compile(regex, flags);
		}
		Matcher mat = pat.matcher(str);
		return mat.replaceAll(ment);
	}

	/**
	 * 获取匹配参数值 eg: regex=(.*)[b](.*)[/b],str=A[b]B[/b],return [1]=A,[2]=B
	 * 
	 * @param regex
	 *            正则表达式
	 * @param str
	 *            目标字符串
	 * @return 匹配参数值
	 */
	public static String[] getMatcherValue(String regex, String str) {
		return getMatcherValue(regex, str, Pattern.CASE_INSENSITIVE);
	}

	/**
	 * 获取匹配参数值
	 * 
	 * @param regex
	 *            正则表达式
	 * @param str
	 *            目标字符串
	 * @param flags
	 *            匹配模式 Pattern.CASE_INSENSITIVE忽略大小写
	 * @return
	 */
	public static String[] getMatcherValue(String regex, String str, int flags) {
		Pattern pat = null;
		if (flags == -1) {
			pat = Pattern.compile(regex);
		} else {
			pat = Pattern.compile(regex, flags);
		}
		Matcher mat = pat.matcher(str);
		if (mat.find()) {
			String[] param = new String[mat.groupCount()];
			for (int i = 0; i < mat.groupCount(); i++) {
				param[i] = mat.group(i + 1);
			}
			return param;
		}
		return null;
	}

	/**
	 * 是否符合正则判定
	 * 
	 * @param regex
	 * @param str
	 * @return
	 */
	public static boolean isTrue(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		return match.matches();
	}
}
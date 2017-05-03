package team.contacts.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.google.gson.Gson;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 工具类
 * @author ZZF
 *
 */
@SuppressWarnings({"resource" })
public class MyTools {
	/**
	 * 生成一个token，格式为分秒时月年天
	 * @return
	 */
	public static String getAToken() {
		Calendar calendar = Calendar.getInstance();
		String year = calendar.get(Calendar.YEAR) + "";
		String month = calendar.get(Calendar.MONTH) + "";
		String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
		String hour = calendar.get(Calendar.HOUR) + "";
		String minute = calendar.get(Calendar.MINUTE) + "";
		String second = calendar.get(Calendar.SECOND) + "";
		String result = minute + second + hour + month + year + day;
		return result;
	}

	/**
	 * 利用阿里大于发送验证码 
	 * @param text 发送内容
	 * @param phone 发送的目标手机号码
	 */
	@SuppressWarnings("unused")
	public static void sendMessage(String text, String phone) {
		TaobaoClient client = new DefaultTaobaoClient("https://eco.taobao.com/router/rest", "23740891",
				"2f6e3affa1ef73fff46188173b157a41");
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		// 公共回传参数，在“消息返回”中会透传回该参数；举例：用户可以传入自己下级的会员ID，在消息返回时，该会员ID会包含在内，用户可以根据该会员ID识别是哪位会员使用了你的应用
		req.setExtend("123456");
		// 短信类型，传入值请填写normal
		req.setSmsType("normal");
		// 短信签名，传入的短信签名必须是在阿里大于“管理中心-短信签名管理”中的可用签名。如“阿里大于”已在短信签名管理中通过审核，则可传入”阿里大于“（传参时去掉引号）作为短信签名。短信效果示例：【阿里大于】欢迎使用阿里大于服务。
		req.setSmsFreeSignName("家庭电话本");
		// 短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码。示例：18600000000,13911111111,13322222222
		req.setRecNum(phone);
		// 短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，传参时需传入{"code":"1234","product":"alidayu"}
		req.setSmsParamString("{\"code\":\"" + text + "\"}");
		// 短信模板ID，传入的模板必须是在阿里大于“管理中心-短信模板管理”中的可用模板。示例：SMS_585014
		req.setSmsTemplateCode("SMS_60455039");
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将对象转化成json字符串
	 * @param list
	 * @param srcType
	 * @return
	 */
	public static String getJson(Object object, Type type) {
		Gson gson = new Gson();
		return gson.toJson(object, type);
	}
	

	/**
	 * 将json数组字符串转化成JavaBean对象
	 * @param json
	 * @param type
	 * @return
	 */
	public static Object getBean(String json, Type type) {
		Gson gson = new Gson();
		return gson.fromJson(json, type);
	}

	/**
	 * 将sql脚本转化成sql语句集,这是针对数据库重建的sql文件，包含Drop和create操作
	 * @throws IOException 
	 */
	public static List<String> loadSql(String sqlPath) throws IOException {
		List<String> sqlList = new ArrayList<String>();
		InputStream sqlFileIn = new FileInputStream(sqlPath);
		StringBuffer sqlSb = new StringBuffer();
		byte[] buff = new byte[1024];
		int byteRead = 0;
		//循环读取输入流中的字节到字节数组中，直到输入流到文件尾
		while ((byteRead = sqlFileIn.read(buff)) != -1) {
			sqlSb.append(new String(buff, 0, byteRead));
		}
		String s = sqlSb.toString();
		//将换行符去除，这是Window下的换行符，而Linux下的为\n
		s = s.replace("\r\n", "");
		int index1,index2;
		while(s.indexOf("DROP")!=-1){
			index1 = s.indexOf("DROP");
			s = s.substring(index1);
			index2 = s.indexOf(";");
			sqlList.add(s.substring(0, index2+1));
			index1 = s.indexOf("CREATE");
			s = s.substring(index1);
			index2 = s.indexOf(";");
			sqlList.add(s.substring(0,index2+1));
		}
		return sqlList;
	}
	
	/**
	 * 返回查找位于src下文件所在目录
	 * @param fileName 文件名
	 * @return
	 */
	public static String getSourcePath(String fileName){
		MyTools myTools = new MyTools();
		//给fileName前面添加斜杠，不然会报错
		fileName = "/"+fileName;
		return myTools.getClass().getResource(fileName).getPath();
	}
	
//	public static void main(String... args){
//		MyTools.sendMessage(MyTools.getAToken(),"15119696401");
//	}
}

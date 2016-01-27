package ali.scut.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpConfirmServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String fileName = "result.html";
	private static final String title = "SignUpResult";
	private static final int bufferSize = 1024 * 8;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		ServletOutputStream sOS = resp.getOutputStream();
		sOS.println("DoGet");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 获得输出
		ServletOutputStream SOS = resp.getOutputStream();
		try {
			// 读入模板
			File file = new File(this.getServletConfig().getServletContext().getRealPath("/") + fileName);
			if (!file.exists()) {
				SOS.println("Templet File Doesn't Exist");
				return;
			}
		    byte[] bytes = new byte[bufferSize];
			// 替换模板
		    StringBuffer signUpResult = new StringBuffer();
		    StringBuffer resultHTML = new StringBuffer();
		    signUpResult.append("Congratulations SignUp Success <br/> Detail Information:<br/>Name : ")
					.append((String) req.getParameter("name"))
					.append("<br/>Email : ")
					.append((String) req.getParameter("email"))
					.append("<br/>Message : ")
					.append((String) req.getParameter("message"));
			InputStream iS = new FileInputStream(file);
			while(iS.read(bytes) != -1){
				resultHTML.append(new String(bytes));
			}
			iS.close();
			int titleIndex = resultHTML.indexOf("_TITLE_");
			resultHTML.replace(titleIndex, titleIndex + "_TITLE_".length(), title);
			int messageIndex = resultHTML.indexOf("_MESSAGE_");
			resultHTML.replace(messageIndex, messageIndex + "_MESSAGE_".length(), signUpResult.toString());
			SOS.println(resultHTML.toString());
		} catch (Exception e) {
			// 处理掉所有异常
			e.printStackTrace();
			SOS.println("Servlet Inner Exception");
			SOS.println(e.toString());
			return;
		}
	}

}

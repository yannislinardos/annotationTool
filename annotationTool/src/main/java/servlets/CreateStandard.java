package servlets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import util.Queries;
import beans.Standard;

/**
 * Servlet implementation class CreateStandard
 */
public class CreateStandard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String[] colors = "c5bb50 a7ca84 5cdbd1 8697b8 04ddf1 1d7e88 839654 d11c8c ea390c fc9efb 4ac567 ab09a9 b81f38 967947 ae75a0 3ddc19 183fd9 7ed4ae f5b48a 3dd6a8 87c578 39a8ab 9ca3bd 9656b8 0de4eb aef81d 700b8d 472df7 48e6d1 372b5e ab6869 75193a 3a7603 ca5fe4 c7b927 29639e ce77ab 7a34c2 b7e74b 702771 46152c 6301b7 092f2f 45170e e27918 ed2e7c 32ff11 71b2b8 f059c9 bd0586 17ce3e b15d4b 855ebc 4f2a36 107ec0 da3644 720cc5 6e8f4d c60874 4a0137 03ae88 6a6725 bb01d8 edccf1 788e30 bc52fc afc518 33768d 1228b4 ec7f23 bcc1a3 22066b 7fee45 9fbfb1 c124a8 ac11f7 300a15 836af9 f338b2 6d74b4 79a8d2 922319 d09d7d f7bd93 9a4987 2000d8 6d7a5d 3d3a78 f176cf d4f767 0436de 1da458 546cad 785110 16ae3d 31c9db 1efd5b 70da27 baadaf 71aeca b9107c 6ea72e d7de47 163786 ba1209 0b05e7 a6d594 df083c 40fccd 219b6b 79d8b9 52c83e d6dbe5 0fc64d caef47 01155d f357c5 e60aa4 6424c9 1319ed 7b5fc9 bed7fd ee65d9 b57abc 514942 10f196 ee3eb3 aa439e df0551 4f1bfa 19d67c 215515 8c08a6 53c77f 7bf0ad bf63d8 bba3c9 8d05d9 1b5d4a 051e7a c5ec5f 82c180 6d3ae1 bd7a1d 85db28 8b3985 75815d 4e585c 4abaa8 09c27b 22e073 e539ea 2ee9d3 e6f6ec 7f5cf7 a29444 5210a3 32ddee 8f66dc e1d8ce c9a6c3 88318f 149c6d cf6c2a cc8718 0a5038 572986 c6bc72 451331 2df11e 935d9a caa771 c69d08 c5b5e6 ef1e1d 594c23 19a09e 862dee da805b 21c9fb 5106d0 0689d5 f00f17 a3080e 980a49 760b8b fe02eb 5a1e24 131071 0263f1 960b35 eae035 3f05fe 02b619 3e5d4e ea9bd1 e7bb6a 5bd900 78ee1a 278d64 89caea 1e3053 26934c b7cc3a 8c668b 1bfeb9 164b00 4b7b77 7cf538 0507fe 2e3342 7b9d47 288a55 e75cb1 683591 4ed092 88dc35 f4145f c78730 cb24e8 628f2a 05e848 18a0d0 c574e0 9a7e72 b7e069 53a83b 704db5 86dd20 4a9652 090874 21f7d4 d9d03c f28a66 c9ca99 faf9a2 732de0 151ace aa1975 b543a1 d1cf7f f125f7 db9d8e 5faf82 19278e b252ef 6d42a0 2c4427 da71a7 f78807 c57125 1e0fea 724e05 c2aa49 2d6000 eeb9bd ddb9e4 db11f9 785c93 aebb2e bcf970 04f48e 8030d5 19b4d9 b69770 9ce5e4 d60c97 9b81de 6b60d2 a89ef8 4d4bbf 27df2d a2de0a deb7eb 79f691 0f389d c9d68c c4a549 02bc6c e71f5e cb9123 634196 e5c1ea 805bee 9878c1 550973 248b57 fe9fa6 e1e20a d69479 eb9274 f38e3c 99a582 207134 93800f d77212 203ea2 e92604 17703e 399c79 6782e8".split(" ");
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String json = request.getParameter("standard");
		String name = request.getParameter("name");
		String processedjson = processJson(json);

		Standard standard = new Standard();

		standard.setJson(processedjson);
		standard.setName(name);
		//		System.out.println(request.getParameter("name"));
		//		System.out.println(request.getParameter("standard"));

		int id = Queries.insertStandard(standard);
		
		if (id == -1){
			System.out.println("Standard NOT created");
			request.setAttribute("check", false);
		} else {
			System.out.println("Standard created");
			request.setAttribute("check", "Standard created with id: " + id);	
		}

		request.getRequestDispatcher("managestandards.jsp").forward(request, response);	
	}

	private String processJson(String standard) {

		String message = standard;

		message = message.replaceFirst(Pattern.quote("\"data\":{}"), "\"data\":{\"color\":\"#ffffff\"}");

		List<String> strList = Arrays.asList(colors);
		Collections.shuffle(strList);
		colors = strList.toArray(new String[strList.size()]);
		
		int i = 0;
		while(message.contains("\"data\":{}")) {
			String colour = colors[i];
			i++;
			
			message = message.replaceFirst(Pattern.quote("\"data\":{}"), "\"data\":{\"color\":\"#" + colour + "80\"}");
			message = message.replaceFirst(Pattern.quote("\"icon\":true"), "\"icon\":\"images/" + colour + ".png\""); 
		}

		return message;
	}

//	private static String generateColor(Random r) {
//		final char [] hex = { '0', '1', '2', '3', '4', '5', '6', '7',
//				'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
//		char [] s = new char[7];
//		int     n = r.nextInt(0x1000000);
//
//		s[0] = '#';
//		for (int i=1;i<7;i++) {
//			s[i] = hex[n & 0xf];
//			n >>= 4;
//		}
//		return new String(s);
//	}
//
//	
//	private static void createImage(String color) throws IOException {		
//		Color col = Color.decode(color);
//
//		BufferedImage b_img = new BufferedImage(15, 15,
//				BufferedImage.TYPE_INT_RGB);
//				Graphics2D graphics = b_img.createGraphics();
//
//				graphics.setPaint (col);
//				graphics.fillRect ( 0, 0, b_img.getWidth(), b_img.getHeight() );
//
//				
//				color= color.substring(1);
//				File outputfile = new File("./src/main/webapp/images/colors/" + color + ".png");
//				ImageIO.write(b_img, "png",outputfile);
//	}
//
//		public static void main(String[] args){
//			Random rand = new Random(); 
//			String c = "";
//			for(int i=0; i<=300 ; i++) {
//				String colour = generateColor(rand);
//				try {
//					createImage(colour);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				c = c + " " + colour.substring(1);
//
//				
//			}
//				System.out.println(c);
//	
//		}

}

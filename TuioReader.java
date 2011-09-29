import java.lang.*;
import java.io.*;
import java.net.*;
import java.lang.*;
import java.nio.*;

class TuioReader
{
	public static String pad(String bs)
	{
		int l = bs.length();
		int offset = 8 - l;
		String r = "";

		for (int i = 0; i < offset; i++)
		{
			r += "0";
		}
		r += bs;
		return r;
	}

	public static int byteToInt(byte b)
	{
		int r;
		if ((int) b >= 0) r = (int) b;
		else r = (b * -1) + 128;

		return r;
	}

	public static String bytesToBinaryString(byte[] b)
	{
		String b0 = Integer.toBinaryString(byteToInt(b[0]));
		String b1 = Integer.toBinaryString(byteToInt(b[1]));
		String b2 = Integer.toBinaryString(byteToInt(b[2]));
		String b3 = Integer.toBinaryString(byteToInt(b[3]));

		return pad(b3) + pad(b2) + pad(b1) + pad(b0);
	}

	public static int bytesToInt(byte[] b)
	{
		String rS = bytesToBinaryString(b);
		int r = Integer.parseInt(rS, 2);

		return r;
	}

	public static float bytesToFloat(byte[] b)
	{
		String rS = bytesToBinaryString(b);
		int h = Integer.parseInt(rS, 2);
		float r = Float.intBitsToFloat(h);

		return r;
	}

	public static void main(String args[])
	{
		try
		{
			Socket skt = new Socket("localhost", 3000);
			BufferedInputStream in = new BufferedInputStream(skt.getInputStream());

			byte[] b = new byte[30];
			int n;

			while (true)
			{
				byte[] header = new byte[4];
				n = in.read(header, 0, 1);
				if ((int) header[0] == 67)
				{
					n = in.read(header, 1, 3);
					if ((int) header[1] == 67 && (int) header[2] == 86)
					{
						System.out.println("Header received");

						byte[] num = new byte[4];
						n = in.read(num, 0, 4);
						int numI = bytesToInt(num);

						System.out.println("Blob count: " + numI);

						for (int i = 0; i < numI; i ++)
						{
							byte[] id = new byte[4];
							n = in.read(id, 0, 4);
							int idI = bytesToInt(id);

							System.out.println("ID: " + idI);

							byte[] x = new byte[4];
							n = in.read(x, 0, 4);
							float xF = bytesToFloat(x);
							System.out.println("x: " + xF);

							byte[] y = new byte[4];
							n = in.read(y, 0, 4);
							float yF = bytesToFloat(y);
							System.out.println("y: " + yF);

							byte[] dx = new byte[4];
							n = in.read(dx, 0, 4);
							//float dxF = bytesToFloat(dx);
							//System.out.println("dx: " + dxF);

							byte[] dy = new byte[4];
							n = in.read(dy, 0, 4);
							//float dyF = bytesToFloat(dy);
							//System.out.println("dy: " + dyF);

							byte[] a = new byte[4];
							n = in.read(a, 0, 4);
							//float aF = bytesToFloat(a);
							//System.out.println("a: " + aF);

							byte[] w = new byte[4];
							n = in.read(w, 0, 4);
							float wF = bytesToFloat(w);
							System.out.println("w: " + wF);

							byte[] h = new byte[4];
							n = in.read(h, 0, 4);
							float hF = bytesToFloat(h);
							System.out.println("h: " + hF);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}


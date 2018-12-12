package wx.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/**
 * 有序UUID生成器
 * @author kain
 *
 */
final class UUIDGen {
	private static AtomicLong lastTime = new AtomicLong(-9223372036854775808L);

	  private static String macAddress = null;

	  private static long clockSeqAndNode = -9223372036854775808L;

	  public static long getClockSeqAndNode()
	  {
	    return clockSeqAndNode;
	  }

	  public static long newTime()
	  {
	    return createTime(System.currentTimeMillis());
	  }

	  public static long createTime(long currentTimeMillis)
	  {
	    long timeMillis = currentTimeMillis * 10000L + 122192928000000000L;
	    while (true)
	    {
	      long current = lastTime.get();
	      if (timeMillis > current) {
	        if (lastTime.compareAndSet(current, timeMillis)) {
	          break;
	        }
	      }
	      else if (lastTime.compareAndSet(current, current + 1L)) {
	        timeMillis = current + 1L;
	        break;
	      }

	    }

	    long time = timeMillis << 32;

	    time |= (timeMillis & 0x0) >> 16;

	    time |= 0x1000 | timeMillis >> 48 & 0xFFF;

	    return time;
	  }

	  public static String getMACAddress()
	  {
	    return macAddress;
	  }

	  static String getFirstLineOfCommand(String[] commands)
	    throws IOException
	  {
	    Process p = null;
	    BufferedReader reader = null;
	    try
	    {
	      p = Runtime.getRuntime().exec(commands);
	      reader = new BufferedReader(new InputStreamReader(p.getInputStream()), 128);

	      return reader.readLine();
	    }
	    finally {
	      if (p != null) {
	        Resource.close(new Object[] { reader, p.getErrorStream(), p.getOutputStream() });
	        p.destroy();
	      }
	    }
	  }

	  static
	  {
	    try
	    {
	      Class.forName("java.net.InterfaceAddress");
	      macAddress = Class.forName("com.eaio.uuid.UUIDGen$HardwareAddressLookup").newInstance().toString();
	    }
	    catch (ExceptionInInitializerError localExceptionInInitializerError)
	    {
	    }
	    catch (ClassNotFoundException localClassNotFoundException)
	    {
	    }
	    catch (LinkageError localLinkageError)
	    {
	    }
	    catch (IllegalAccessException localIllegalAccessException)
	    {
	    }
	    catch (InstantiationException localInstantiationException)
	    {
	    }
	    catch (SecurityException localSecurityException)
	    {
	    }

	    if (macAddress == null)
	    {
	      Process p = null;
	      BufferedReader in = null;
	      try
	      {
	        String osname = System.getProperty("os.name", ""); String osver = System.getProperty("os.version", "");

	        if (osname.startsWith("Windows")) {
	          p = Runtime.getRuntime().exec(new String[] { "ipconfig", "/all" }, null);
	        }
	        else if ((osname.startsWith("Solaris")) || (osname.startsWith("SunOS")))
	        {
	          if (osver.startsWith("5.11")) {
	            p = Runtime.getRuntime().exec(new String[] { "dladm", "show-phys", "-m" }, null);
	          }
	          else
	          {
	            String hostName = getFirstLineOfCommand(new String[] { "uname", "-n" });
	            if (hostName != null) {
	              p = Runtime.getRuntime().exec(new String[] { "/usr/sbin/arp", hostName }, null);
	            }

	          }

	        }
	        else if (new File("/usr/sbin/lanscan").exists()) {
	          p = Runtime.getRuntime().exec(new String[] { "/usr/sbin/lanscan" }, null);
	        }
	        else if (new File("/sbin/ifconfig").exists()) {
	          p = Runtime.getRuntime().exec(new String[] { "/sbin/ifconfig", "-a" }, null);
	        }

	        if (p != null) {
	          in = new BufferedReader(new InputStreamReader(p.getInputStream()), 128);

	          String l = null;
	          while ((l = in.readLine()) != null) {
	            macAddress = MACAddressParser.parse(l);
	            if ((macAddress != null) && (Hex.parseShort(macAddress) != 255))
	            {
	              break;
	            }
	          }
	        }
	      }
	      catch (SecurityException localSecurityException1)
	      {
	      }
	      catch (IOException localIOException)
	      {
	      }
	      finally
	      {
	        if (p != null) {
	          Resource.close(new Object[] { in, p.getErrorStream(), p.getOutputStream() });
	          p.destroy();
	        }
	      }

	    }

	    if (macAddress != null) {
			clockSeqAndNode |= Hex.parseLong(macAddress);
		}

	    else {
	      try
	      {
	        byte[] local = InetAddress.getLocalHost().getAddress();
	        clockSeqAndNode |= local[0] << 24 & 0xFF000000;
	        clockSeqAndNode |= local[1] << 16 & 0xFF0000;
	        clockSeqAndNode |= local[2] << 8 & 0xFF00;
	        clockSeqAndNode |= local[3] & 0xFF;
	      }
	      catch (UnknownHostException ex) {
	        clockSeqAndNode |= (long)(Math.random() * 2147483647.0D);
	      }

	    }

	    clockSeqAndNode |= (long)(Math.random() * 16383.0D) << 48;
	  }

	  static class HardwareAddressLookup
	  {
	    public String toString() {
	      String out = null;
	      try {
	        Enumeration<?> ifs = NetworkInterface.getNetworkInterfaces();
	        if (ifs != null) {
	          while (ifs.hasMoreElements()) {
	            NetworkInterface iface = (NetworkInterface)ifs.nextElement();
	            byte[] hardware = iface.getHardwareAddress();
	            if ((hardware != null) && (hardware.length == 6) && (hardware[1] != -1))
	            {
	              out = Hex.append(new StringBuilder(36), hardware).toString();
	              break;
	            }
	          }
	        }
	      }
	      catch (SocketException localSocketException) {
	      }
	      return out;
	    }
	  }
	  
	  public static class MACAddressParser
	  {
	    public static final Pattern MAC_ADDRESS = Pattern.compile("((?:[A-F0-9]{1,2}[:-]){5}[A-F0-9]{1,2})|(?:0x)(\\d{12})(?:.+ETHER)", 2);

	    static String parse(String in)
	    {
	      Matcher m = MAC_ADDRESS.matcher(in);
	      if (m.find()) {
	        String g = m.group(2);
	        if (g == null) {
	          g = m.group(1);
	        }
	        return g == null ? g : g.replace('-', ':');
	      }
	      return null;
	    }
	  }
}

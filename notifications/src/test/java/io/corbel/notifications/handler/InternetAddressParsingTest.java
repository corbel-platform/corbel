package io.corbel.notifications.handler;

import org.junit.Assert;
import org.junit.Test;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * @author Alexander De Leon (alex.deleon@devialab.com)
 */
public class InternetAddressParsingTest {

    @Test
    public void testParse() throws AddressException {
        InternetAddress[] addresses = InternetAddress.parse("MAPFRE Welcome Pack Digital <noreply@jvsp.co>");
        Assert.assertEquals(1, addresses.length);
        InternetAddress address = addresses[0];
        Assert.assertEquals("MAPFRE Welcome Pack Digital", address.getPersonal());
        Assert.assertEquals("noreply@jvsp.co", address.getAddress());
    }

    @Test
    public void testParseAddressOnly() throws AddressException {
        InternetAddress[] addresses = InternetAddress.parse("noreply@jvsp.co");
        Assert.assertEquals(1, addresses.length);
        InternetAddress address = addresses[0];
        Assert.assertNull(address.getPersonal());
        Assert.assertEquals("noreply@jvsp.co", address.getAddress());

    }

}

/*
 * Copyright (c) Patrick Huber (gmail: stackmagic)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.swisstech.bitly.test;

import static net.swisstech.bitly.test.util.AssertUtil.assertGreater;
import static net.swisstech.bitly.test.util.AssertUtil.assertIn;
import static net.swisstech.bitly.test.util.TestUtil.printAndVerify;
import static org.testng.Assert.assertEquals;
import net.swisstech.bitly.model.Response;
import net.swisstech.bitly.model.v3.LinkSharesRolledUpResponse;
import net.swisstech.bitly.model.v3.LinkSharesRolledUpResponse.LinkShare;

import org.testng.annotations.Test;

/**
 * <p>
 * Integration Test for the <code>/v3/link/shares</code> call
 * </p>
 * 
 * <p>
 * Please see the bit.ly documentation for the <a href="http://dev.bitly.com/link_metrics.html#v3_link_shares">/v3/link/shares</a> request.
 * </p>
 * 
 * @author Patrick Huber (gmail: stackmagic)
 */
public class LinkSharesRolledUpIntegrationTest extends AbstractBitlyClientIntegrationTest {

	@Test
	public void callLinkSharesRolledUp() {
		Response<LinkSharesRolledUpResponse> resp = getClient().linkSharesRolledUp() //
				.setLink("http://bit.ly/LTlncm") //
				.setUnit("day") //
				.setUnits(-1) //
				.setTimezone(0) //
				.setLimit(1000) //
				.call();

		printAndVerify(resp, LinkSharesRolledUpResponse.class);

		assertGreater(resp.data.shares.size(), 0);
		for (LinkShare share : resp.data.shares) {
			assertGreater(share.shares, 0);
			assertIn(share.share_type, "tw", "fb");
		}

		assertGreater(resp.data.total_shares, 0);
		assertEquals(resp.data.unit, "day");
		assertEquals(resp.data.units, -1);
		assertEquals(resp.data.tz_offset, 0);
	}
}

package net.marinalan.aequilibrium.transformers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TransformersApplicationTests {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	DataSource dataSource;

	@Autowired
	EntityManager em;

	@Autowired
	TransformerService transformerService;

	@Before
	public void before() {
		System.out.println("Before");
	}

	@After
	public void after() {
		System.out.println("After");
		em.createNativeQuery("truncate table transformer").executeUpdate();
	}

	@Test
	public void printTransformer() {
		Transformer t = new Transformer();
		t.setName("Soundwave");
		t.setType(TransformerType.Decepticon);
		t.setStrength(8);
		t.setIntelligence(9);
		t.setSpeed(2);
		t.setEndurance(6);
		t.setRank(7);
		t.setCourage(5);
		t.setFirepower(6);
		t.setSkill(10);

		String expectedDescription = "Soundwave, D, 8,9,2,6,7,5,6,10";
		System.out.println(t.toString());
		assertEquals(expectedDescription, t.toString());

		Transformer t1 = new Transformer("Bluestreak", TransformerType.Autobot, 6,6,7,9,5,2,9,7);
		expectedDescription = "Bluestreak, A, 6,6,7,9,5,2,9,7";
		System.out.println(t1.toString());
		assertEquals(expectedDescription, t1.toString());
	}

	@Test
	public void checkSeparateSourceForTests(){
		try {
			System.out.println(dataSource.getConnection().getMetaData().getURL());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void checkCrudeOperations(){
		Transformer t1 = transformerService.create(new Transformer("Soundwave", TransformerType.Decepticon, 8,9,2,6,7,5,6,10));
		Transformer t2 = transformerService.create(new Transformer("Bluestreak", TransformerType.Autobot, 6,6,7,9,5,2,9,7));
		Transformer t3 = transformerService.create(new Transformer("Hubcap", TransformerType.Autobot, 4,4,4,4,4,4,4,4));

		Transformer tFound = transformerService.findById(t1.getId());

		String expectedDescription = "Soundwave, D, 8,9,2,6,7,5,6,10";
		assertNotNull(tFound);
		assertEquals(t1, tFound);
		System.out.println(tFound.toString());

		assertEquals(expectedDescription, tFound.toString());

		List<Transformer> bots = transformerService.getAll();
		assertEquals(3, bots.size());

		// creating some detached copy
		Transformer tUpdated = new Transformer();
		tUpdated.setId(t1.getId());
		tUpdated.setName("SoundWave 2");
		tUpdated.setType(t1.getType());
		tUpdated.setStrength((t1.getStrength()));
		tUpdated.setIntelligence((t1.getIntelligence()));
		tUpdated.setSpeed((t1.getSpeed()));
		tUpdated.setEndurance((t1.getEndurance()));
		tUpdated.setRank(t1.getRank());
		tUpdated.setCourage(t1.getCourage());
		tUpdated.setFirepower(8);
		tUpdated.setSkill(4);

		transformerService.update(t1.getId(), tUpdated);

		System.out.println("After update: ");
		Transformer tAfterUpdate = transformerService.findById(t1.getId());
		assertNotNull(tAfterUpdate);

		expectedDescription = "SoundWave 2, D, 8,9,2,6,7,5,8,4";
		System.out.println(tAfterUpdate.toString());

		assertEquals(expectedDescription, tAfterUpdate.toString());
		assertEquals(tUpdated, tAfterUpdate);

		transformerService.deleteById(t2.getId());
		bots = transformerService.getAll();
		assertEquals(2, bots.size());
	}

	@Test
	public void getMultipleByIds() {
		Transformer t1 = transformerService.create(new Transformer("Soundwave", TransformerType.Decepticon, 8,9,2,6,7,5,6,10));
		Transformer t2 = transformerService.create(new Transformer("Bluestreak", TransformerType.Autobot, 6,6,7,9,5,2,9,7));
		Transformer t3 = transformerService.create(new Transformer("Hubcap", TransformerType.Autobot, 4,4,4,4,4,4,4,4));
		Transformer t4 = transformerService.create(new Transformer("Optimus Prime", TransformerType.Autobot, 6,7,8,7,6,5,4,3));
		Transformer t5 = transformerService.create(new Transformer("Predaking", TransformerType.Decepticon, 4,4,4,4,4,4,4,4));
		Transformer t6 = transformerService.create(new Transformer("Bender", TransformerType.Decepticon, 8,5,7,6,5,3,8,5));

		List<Integer> ids = new ArrayList<Integer>();
		ids.add(t1.getId());
		ids.add(t3.getId());
		ids.add(t5.getId());
		ids.add(t6.getId());

		List<Transformer> bots = transformerService.geByMultipleIds(ids);
		assertEquals(4, bots.size());

		assertEquals("Soundwave, D, 8,9,2,6,7,5,6,10", bots.get(0).toString());
		assertEquals("Hubcap, A, 4,4,4,4,4,4,4,4", bots.get(1).toString());
		assertEquals("Predaking, D, 4,4,4,4,4,4,4,4", bots.get(2).toString());
		assertEquals("Bender, D, 8,5,7,6,5,3,8,5", bots.get(3).toString());
	}

	@Test
	public void checkRankedAutobotsWithIds() {
		Transformer t1 = transformerService.create(new Transformer("Soundwave", TransformerType.Decepticon, 8,9,2,6,7,5,6,10));
		Transformer t2 = transformerService.create(new Transformer("Bluestreak", TransformerType.Autobot, 6,6,7,9,1,2,9,7));
		Transformer t3 = transformerService.create(new Transformer("Hubcap", TransformerType.Autobot, 4,4,4,4,1,4,4,4));
		Transformer t4 = transformerService.create(new Transformer("Optimus Prime", TransformerType.Autobot, 6,7,8,7,2,5,4,3));
		Transformer t5 = transformerService.create(new Transformer("Predaking", TransformerType.Decepticon, 4,4,4,4,4,4,4,4));
		Transformer t6 = transformerService.create(new Transformer("Bender", TransformerType.Decepticon, 8,5,7,6,5,3,8,5));

		List<Integer> ids = new ArrayList<Integer>();
		ids.add(t1.getId());
		ids.add(t2.getId());
		ids.add(t3.getId());
		ids.add(t4.getId());
		ids.add(t6.getId());

		List<Transformer> bots = transformerService.getRankedAutobotsWithIds(ids);
		assertEquals(3, bots.size());

		assertEquals("Bluestreak, A, 6,6,7,9,1,2,9,7", bots.get(0).toString());
		assertEquals("Hubcap, A, 4,4,4,4,1,4,4,4", bots.get(1).toString());
		assertEquals("Optimus Prime, A, 6,7,8,7,2,5,4,3", bots.get(2).toString());
	}

	@Test
	public void checkRankedDecepticonsWithIds() {
		Transformer t1 = transformerService.create(new Transformer("Soundwave", TransformerType.Decepticon, 8,9,2,6,7,5,6,10));
		Transformer t2 = transformerService.create(new Transformer("Bluestreak", TransformerType.Autobot, 6,6,7,9,1,2,9,7));
		Transformer t3 = transformerService.create(new Transformer("Hubcap", TransformerType.Autobot, 4,4,4,4,1,4,4,4));
		Transformer t4 = transformerService.create(new Transformer("Optimus Prime", TransformerType.Autobot, 6,7,8,7,2,5,4,3));
		Transformer t5 = transformerService.create(new Transformer("Predaking", TransformerType.Decepticon, 4,4,4,4,4,4,4,4));
		Transformer t6 = transformerService.create(new Transformer("Bender", TransformerType.Decepticon, 8,5,7,6,5,3,8,5));

		List<Integer> ids = new ArrayList<Integer>();
		ids.add(t1.getId());
		ids.add(t2.getId());
		ids.add(t3.getId());
		ids.add(t4.getId());
		ids.add(t6.getId());

		List<Transformer> bots = transformerService.getRankedDecepticonsWithIds(ids);
		assertEquals(2, bots.size());

		assertEquals("Bender, D, 8,5,7,6,5,3,8,5", bots.get(0).toString());
		assertEquals("Soundwave, D, 8,9,2,6,7,5,6,10", bots.get(1).toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldBeExceptionWhenOfSamweKind(){
		Transformer t1 = new Transformer("Bluestreak", TransformerType.Autobot, 6,6,7,9,5,2,9,7);
		Transformer t2 = new Transformer("Optimus Prime", TransformerType.Autobot, 6,6,7,9,5,2,9,7);

		t1.matchOutcome(t2);
	}

	@Test
	public void matchOutcomeBetweenTwoBots() {
		Transformer t1 = new Transformer("Bluestreak", TransformerType.Decepticon, 6,6,7,9,5,2,9,7);
		Transformer t2 = new Transformer("Optimus Prime", TransformerType.Autobot, 6,6,7,9,5,2,9,7);

		System.out.println("One is special");
		System.out.println(t1.toString()+" vs "+t2.toString());
		assertEquals(1, t2.matchOutcome((t1)));
		assertEquals(-1, t1.matchOutcome((t2)));

		System.out.println("Both special");
		t1.setName("Predaking");
		System.out.println(t1.toString()+" vs "+t2.toString());
		assertEquals(-2, t1.matchOutcome(t2));
		assertEquals(-2, t2.matchOutcome(t1));

		System.out.println("One is 4 or more points in courage");
		t1.setName("Soundwave");
		t2.setName("Strongest");
		t1.setCourage(6);
		System.out.println(t1.toString()+" vs "+t2.toString());
		assertEquals(1, t1.matchOutcome((t2)));
		assertEquals(-1, t2.matchOutcome((t1)));

		System.out.println("One is 3 or more points in strength");
		t1.setCourage(t2.getCourage());
		t1.setStrength(9);
		System.out.println(t1.toString()+" vs "+t2.toString());
		assertEquals(1, t1.matchOutcome((t2)));
		assertEquals(-1, t2.matchOutcome((t1)));

		System.out.println("One is 3 or more points in skill");
		t1.setStrength(t2.getStrength());
		t1.setSkill(10);
		System.out.println(t1.toString()+" vs "+t2.toString());
		assertEquals(1, t1.matchOutcome((t2)));
		assertEquals(-1, t2.matchOutcome((t1)));

		t1.setSkill(t2.getSkill());
		t1.setIntelligence(7);
		t1.setSpeed(8);
		System.out.println("Compare by overall rating");
		System.out.println(t1.toString()+" vs "+t2.toString());
		assertEquals(1, t1.matchOutcome((t2)));
		assertEquals(-1, t2.matchOutcome((t1)));

		t1.setIntelligence(t2.getIntelligence());
		t1.setSpeed(t2.getSpeed());
		System.out.println("Should be a tie");
		System.out.println(t1.toString()+" vs "+t2.toString());
		assertEquals(0, t1.matchOutcome((t2)));
		assertEquals(0, t2.matchOutcome((t1)));
	}

	@Test
	public void determineWinningTeam() {
		Transformer t1 = transformerService.create(new Transformer("Soundwave", TransformerType.Decepticon, 8,9,2,6,7,5,6,10));
		Transformer t2 = transformerService.create(new Transformer("Bluestreak", TransformerType.Autobot, 6,6,7,9,1,2,9,7));
		Transformer t3 = transformerService.create(new Transformer("Hubcap", TransformerType.Autobot, 4,4,4,4,2,4,4,4));
		Transformer t4 = transformerService.create(new Transformer("Optimus Prime", TransformerType.Autobot, 6,7,8,7,2,5,4,3));
		Transformer t5 = transformerService.create(new Transformer("Predaking", TransformerType.Decepticon, 4,4,4,4,4,4,4,4));
		Transformer t6 = transformerService.create(new Transformer("Bender", TransformerType.Decepticon, 8,5,7,6,5,3,8,5));

		List<Integer> ids = new ArrayList<Integer>();
		ids.add(t1.getId());
		ids.add(t2.getId());
		ids.add(t3.getId());

		MatchResult mr = transformerService.determineWinner(ids);
		assertEquals(1, mr.getNumBattles());
		assertEquals(t1, mr.getWinner());

		ArrayList<Transformer> survivingLosers = mr.getSurvivingLosers();
		System.out.println(survivingLosers);
		assertEquals(t3.toString(), survivingLosers.get(0).toString());

		System.out.println(mr);
	}
}

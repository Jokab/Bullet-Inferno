package se.dat255.bulletinferno.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.dat255.bulletinferno.test.Common;


public class TimerImplTest {
	
	@BeforeClass
	public static void beforeTests() {
		Common.loadEssentials();	
	}
	
	private class TimerableMockup implements Timerable { 
		private boolean gotACall = false;
		private Timer source = null;
		@Override
		public void onTimeout(Timer source, float timeSinceLast) {
			gotACall = true;
			this.source = source;
		}
		
		public boolean gotACall() {
			return gotACall;
		}
		
		public Timer getCallSource() {
			return source;
		}
		
	};
	
	private TimerableMockup timerableMockup;
	
	@Before
	public void createMockup() {
		timerableMockup = new TimerableMockup();
	}
	
	@Test
	public void testTimerImpl() {
		// Constructs a new timer with time=0 
		Timer timer = new TimerImpl(-3.9f);
		assertTrue(timer.getTimeLeft() == 0);
	}

	@Test
	public void testGetTimeLeft() {
		// Constructs a new timer with time=0 
		Timer timer = new TimerImpl();
		assertTrue(timer.getTimeLeft() == 0);
		
		timer = new TimerImpl(1.2f);
		assertTrue(timer.getTimeLeft() == 1.2f);
	}

	@Test
	public void testIsFinished() {
		// Sets time to 0, i.e. it's finished
		Timer timer = new TimerImpl();
		assertTrue(timer.isFinished());
		
		// Sets time to 0.1 sec
		timer = new TimerImpl(0.1f);
		assertTrue(!timer.isFinished());
		// start timer and update with 0.1 sec
		timer.start();
		timer.update(0.1f);
		assertTrue(timer.isFinished());
		
	}

	@Test
	public void testGetInitialValue() {
		float initialtime = 1.4f;
		Timer timer = new TimerImpl(initialtime);
		assertTrue(initialtime == timer.getInitialValue());
		
		// Update some so that time left isn't initial value
		timer.start();
		timer.update(1);
		assertTrue(initialtime == timer.getInitialValue());
	}

	@Test
	public void testSetTime() {
		Timer timer = new TimerImpl(3);
		timer.start();
		timer.update(1);
		// Set time to 8
		timer.setTime(8);
		timer.start();
		// Run some updates
		timer.update(6);
		assertTrue(timer.getTimeLeft() + " " , timer.getTimeLeft() == 2);
		assertTrue(timer.getInitialValue() == 8);
		
		timer.setTime(-4);
		assertTrue(timer.getTimeLeft() == 0);
	}

	@Test
	public void testStart() {
		Timer timer = new TimerImpl(2);
		// Timer isn't running now, i.e. update
		// shouldn't have an affect
		timer.update(1);
		assertTrue(timer.getTimeLeft() == 2);
		
		// Start timer and run some updates
		timer.start();
		assertTrue(timer.getTimeLeft() == 2);
		timer.update(0.5f);
		assertTrue(timer.getTimeLeft() == 1.5f);
	}

	@Test
	public void testStop() {
		Timer timer = new TimerImpl(2);
		
		// Start timer and run some updates
		timer.start();
		timer.update(0.5f);
				
		// Stop timer and run some updates
		timer.stop();
		timer.update(1f);
		assertTrue(timer.getTimeLeft() == 0);
	}

	@Test
	public void testPause() {
		Timer timer = new TimerImpl(2);
		
		// Start timer and run some updates
		timer.start();
		timer.update(0.5f);
		
		// Pause timer and run some updates
		timer.pause();
		timer.update(1f);
		assertTrue(timer.getTimeLeft() == 1.5f);
		
		// Start timer again and run to check
		// if the update is affecting timer again
		timer.start();
		timer.update(1f);
		assertTrue(timer.getTimeLeft() == 0.5f);
	}

	@Test
	public void testReset() {
		Timer timer = new TimerImpl(2);
		timer.start();
		timer.update(1);
		
		timer.reset();
		assertTrue(timer.getTimeLeft() == 2);
		// Timer is stopped by restart 
		// and thereby shouldn't be affected
		// by an update
		
		timer.update(1);
		assertTrue(timer.getTimeLeft() == 2);
	}

	@Test
	public void testRestart() {
		Timer timer = new TimerImpl(2);
		timer.start();
		timer.update(1);
		
		timer.restart();
		assertTrue(timer.getTimeLeft() == 2);
		// Timer is started by restart 
		// and thereby should be affected
		// by an update
		
		timer.update(1);
		assertTrue(timer.getTimeLeft() == 1);
	}

	@Test
	public void testIsContinuous() {
		Timer timer = new TimerImpl(2, true);
		assertTrue(timer.isContinuous());
		
		timer.start();
		timer.update(2);
		// Timer has reached it's count down and should 
		// now start again with 2 sec to count down
		
		timer.update(1);
		assertTrue(timer.getTimeLeft() == 1);
	}

	@Test
	public void testSetContinuous() {
		Timer timer = new TimerImpl(2, false);
		assertTrue(!timer.isContinuous());
		
		timer.start();
		timer.update(1);
		timer.setContinuous(true);
		assertTrue(timer.isContinuous());
		timer.update(1);
		// Timer has reached it's count down and should 
		// now start again with 2 sec to count down,
		// since we changed it to continuous
		
		timer.update(1);
		assertTrue(timer.getTimeLeft() == 1);
		
		timer.setContinuous(false);
		
		// Timer shouldn't start again since 
		// continuous is not false
		timer.update(2);
		assertTrue(timer.isFinished());
	}

	@Test
	public void testRegisterListener() {
		Timer timer = new TimerImpl(4);
		timer.start();
		timer.registerListener(timerableMockup);
		
		assertTrue(!timerableMockup.gotACall());
		
		timer.update(4);
		
		assertTrue(timerableMockup.gotACall());
		assertTrue(timerableMockup.getCallSource() == timer);
		
	}

	@Test
	public void testUnregisterListener() {
		Timer timer = new TimerImpl(4, true);
		
		timer.registerListener(timerableMockup);
		
		timer.unregisterListener(timerableMockup);
		timer.update(4);
		
		assertFalse(timerableMockup.gotACall());
	}

	@Test
	public void testUpdate() {
		Timer timer = new TimerImpl(3);
		timer.start();
		timer.update(0.5f);
		assertTrue(2.5f == timer.getTimeLeft());
		
		timer.update(2.5f);
		assertTrue(timer.getTimeLeft() == 0);
		assertTrue(timer.isFinished());
		
		// Shouldn't have an affect since
		// timer should be stopped
		timer.update(1);
		assertTrue(timer.getTimeLeft() == 0);
		assertTrue(timer.isFinished());
	}

}

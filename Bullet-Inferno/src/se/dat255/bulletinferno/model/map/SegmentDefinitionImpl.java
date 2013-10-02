package se.dat255.bulletinferno.model.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.Game;

public enum SegmentDefinitionImpl implements SegmentDefinition {
	WATER(SliceDefinitionImpl.WATER, SliceDefinitionImpl.WATER, new ArrayList<SliceDefinitionImpl>() {{ 
		add(SliceDefinitionImpl.WATER);}}),
		
	MOUNTAIN(SliceDefinitionImpl.MOUNTAIN_1, SliceDefinitionImpl.MOUNTAIN_8, 
			new ArrayList<SliceDefinitionImpl>() {{ 
					add(SliceDefinitionImpl.MOUNTAIN_2);
					add(SliceDefinitionImpl.MOUNTAIN_3);
					add(SliceDefinitionImpl.MOUNTAIN_4);
					add(SliceDefinitionImpl.MOUNTAIN_5);
					add(SliceDefinitionImpl.MOUNTAIN_6);
					add(SliceDefinitionImpl.MOUNTAIN_7);
				}});	
	
	private final SliceDefinitionImpl entrySlice;
	private final SliceDefinitionImpl exitSlice;
	private final List<SliceDefinitionImpl> suitableSlices;
	
	SegmentDefinitionImpl(SliceDefinitionImpl entry, SliceDefinitionImpl exit, List<SliceDefinitionImpl> suitableSlices) {
		this.entrySlice = entry;
		this.exitSlice = exit;
		this.suitableSlices = suitableSlices;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Segment createSegment(Game game, Vector2 position, int sliceAmount) {
		List<SliceDefinitionImpl> sliceDefinitonsPath = getSlides(sliceAmount);
		List<Slice> slices = new ArrayList<Slice>(sliceDefinitonsPath.size());
		Vector2 slicePosition = position.cpy();
		System.out.println(sliceDefinitonsPath);
		for(SliceDefinitionImpl sliceType : sliceDefinitonsPath) {
			slices.add(sliceType.createSlice(game, slicePosition));
		}
		return new SegmentImpl(slices, position);
	}
	
	List<SliceDefinitionImpl> getSlides(int amount)  {
		List<List<SliceDefinitionImpl>> exceptions = new ArrayList<List<SliceDefinitionImpl>>(amount);
		for(int i = 0; i < amount; i++) {
			exceptions.add(new LinkedList<SliceDefinitionImpl>());
		}

		List<SliceDefinitionImpl> path = new ArrayList<SliceDefinitionImpl>(amount);
		boolean foundAMatch = false;
		SliceDefinitionImpl current = entrySlice;
		path.add(entrySlice);
		
		for(int i = 0; i < amount - 2; i++) {
			// Shuffle around the slices to randomize path
			Collections.shuffle(suitableSlices);
			foundAMatch = false;
			
			// Go through all the suitable slices to find a matching one
			for(SliceDefinitionImpl slice : suitableSlices) {
				// Find a matching slice that is not in the exceptions list
				if(slice.getEntryHeight() == current.getExitHeight() 
						&& !exceptions.get(i).contains(slice)) {
					// If the tile to be found is the second to last one 
					// i.e. the one before exit slice
					if(i == (amount - 3)) {
						// make sure it also matches the exit slice
						if(slice.getExitHeight() == exitSlice.getEntryHeight()) {
							path.add(slice);
							current = slice;
							foundAMatch = true;
							break;
						}
					} else {
						path.add(slice);
						current = slice;
						foundAMatch = true;
						break;
					}
				}
			}
			
			
			
			if(!foundAMatch) {
				if(i == 0) {
					throw new RuntimeException("Coudln't find a way from specified " +
							"entry slice to end slice " + i);
				}
				
				// Remove current slice from path
				if(path.size() > i) {
					path.remove(i);
				}

				// Erase exception list on this stage because this path leads nowhere
				// and won't be tried again
				exceptions.get(i).clear();
				// Add the current slice as an exception to the past one
				exceptions.get(i-1).add(current);
				
				// Set the past one to be the current to find another slice mate
				current = path.get(i-1);
				i -= 2;
			}
		}	
		path.add(exitSlice);
		return path;
	}
}

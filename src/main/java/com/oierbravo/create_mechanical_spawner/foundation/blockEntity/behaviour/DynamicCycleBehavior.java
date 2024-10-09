package com.oierbravo.create_mechanical_spawner.foundation.blockEntity.behaviour;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class DynamicCycleBehavior extends BlockEntityBehaviour {

    public static final BehaviourType<DynamicCycleBehavior> TYPE = new BehaviourType<>();
	public DynamicCycleBehaviorSpecifics specifics;
	private int prevRunningTicks;
	private int runningTicks;
	private int processingTime;
	private int currentTime;
	private boolean running;
	private boolean finished;

	public interface DynamicCycleBehaviorSpecifics {

		void onCycleCompleted();
		float getKineticSpeed();
		int getProcessingTime();
		boolean tryProcess(boolean simulate);
		void playSound();
	}

	public <T extends SmartBlockEntity & DynamicCycleBehaviorSpecifics> DynamicCycleBehavior(T te) {
		super(te);
		this.specifics = te;
		processingTime = 0;
		currentTime = 0;
	}

	@Override
	public void read(CompoundTag compound, boolean clientPacket) {
		currentTime = compound.getInt("CurrentTime");
		processingTime = compound.getInt("ProcessingTime");
		running = compound.getBoolean("Running");
		finished = compound.getBoolean("Finished");
		super.read(compound, clientPacket);
	}

	@Override
	public void write(CompoundTag compound, boolean clientPacket) {
		compound.putInt("CurrentTime", currentTime);
		compound.putInt("ProcessingTime", processingTime);
		compound.putBoolean("Running", running);
		compound.putBoolean("Finished", finished);
		super.write(compound, clientPacket);
	}

	public void start() {
		running = true;
		currentTime = 0;
		processingTime = specifics.getProcessingTime();
		blockEntity.sendData();
	}
	public void stop(){
		running = false;
		currentTime = 0;
		processingTime = 0;
	}

	@Override
	public BehaviourType<?> getType() {
		return TYPE;
	}

	@Override
	public void tick() {
		super.tick();

		Level level = getWorld();
		if (!running || level == null) {
			if (level != null && !level.isClientSide) {

				if (specifics.getKineticSpeed() == 0)
					return;

				if (specifics.tryProcess( true))
					start();
			}
			return;
		}
		if(running)
			currentTime += getRunningTickSpeed();

		if (currentTime >= getProccessingTime() && specifics.getKineticSpeed() != 0) {
			specifics.playSound();
			if (!level.isClientSide){
				stop();
				apply();
				specifics.onCycleCompleted();
				blockEntity.sendData();
			}
		}
	}

	public float getProgress(float partialTicks){
		int runningTicks = Math.abs(this.runningTicks);
		float ticks = Mth.lerp(partialTicks, prevRunningTicks, runningTicks);
		return ticks/ getProccessingTime() * 100;
	}
	protected int getProccessingTime(){
		return processingTime;
	}

	protected void apply() {
		Level level = getWorld();

		if (level.isClientSide)
			return;

		if (specifics.tryProcess(false))
			blockEntity.sendData();
	}

	public int getRunningTickSpeed() {
		float speed = specifics.getKineticSpeed();
		if (speed == 0)
			return 0;
		return (int) Mth.lerp(Mth.clamp(Math.abs(speed) / 512f, 0, 1), 1, 30);
	}
	public int getProgressPercent() {
		if(!running)
			return 0;
		return Mth.clamp(currentTime * 100 / (getProccessingTime()), 0,100);
	}
}

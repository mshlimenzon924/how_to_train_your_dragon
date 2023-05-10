import processing.core.PImage;

import java.util.*;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Tree extends PlantEntity {

    public Tree(String id, Point position, List<PImage> images, int imageIndex, int animationPeriod, int actionPeriod, int health) {
        super(id, position, images, imageIndex, animationPeriod, actionPeriod, health);
    }

    protected void executeActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        if (!transformPlant(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }
    protected boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (getHealth() <= 0) {
            Stump stump = Factory.createStump(getId(), getPosition(), imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);

            return true;
        }

        return false;
    }

}
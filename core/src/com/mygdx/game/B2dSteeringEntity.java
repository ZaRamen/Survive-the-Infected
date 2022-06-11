package com.mygdx.game;

import Helper.SteeringUtils;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class B2dSteeringEntity implements Steerable<Vector2>
{
    public void setBody(Body body) {
        this.body = body;
    }

    private Body body;
    private boolean tagged;
    private float boundingRadius;
    private float maxLinearSpeed, maxLinearAcceleration;
    private float maxAngularSpeed, maxAngularAcceleration;

    //how it behaves
    private SteeringBehavior<Vector2> behavior;
    //move the body based on the behavior
    private SteeringAcceleration<Vector2> steeringOutput;

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    private float delta;
    //area that's in, how much space it takes up
    public B2dSteeringEntity(Body body, float boundingRadius, float maxLinearSpeed, float maxLinearAcceleration)
    {
        this.body = body;
        this.boundingRadius = boundingRadius;
        this.maxLinearSpeed = maxLinearSpeed;
        this.maxLinearAcceleration = maxLinearAcceleration;
        this.maxAngularSpeed = 30;
        this.maxAngularAcceleration = 5;
        this.steeringOutput = new SteeringAcceleration<>(new Vector2());
        this.tagged = false;
        this.body.setUserData(this);
    }
    public void attack(Camera camera, Player player)
    {

    }

    public void update(float delta)
    {
        if(behavior != null)
        {
            this.delta = delta;
            //does all the ai movement
            behavior.calculateSteering(steeringOutput);

            // Apply steering acceleration to move this agent
            applySteering(delta);
        }
    }
    public void applySteering(float delta)
    {
        boolean anyAcceleration = false;
        if(!steeringOutput.linear.isZero())
        {
            Vector2 force = steeringOutput.linear.scl(delta);
            force.x = force.x * 64;
            force.y = force.y * 64;
            body.applyLinearImpulse(force, body.getPosition(), true);
            anyAcceleration = true;
        }
        if(anyAcceleration)
        {
            Vector2 velocity = body.getLinearVelocity();
            //makes sure to compensate for both x and y velocity
            float currentSpeedSquare = velocity.len2();
            float maxLinearSpeed = getMaxLinearSpeed();

            if (currentSpeedSquare > maxLinearSpeed * maxLinearSpeed)
            {
                //divide
                body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float)Math.sqrt(currentSpeedSquare)));
            }

            // Cap the angular speed
            float maxAngVelocity = getMaxAngularSpeed();
            if (body.getAngularVelocity() > maxAngVelocity) {
                body.setAngularVelocity(maxAngVelocity);
            }
        }

    }
    public Body getBody()
    {
        return body;
    }
    public void setBehavior(SteeringBehavior<Vector2> behavior)
    {
        this.behavior = behavior;
    }
    public SteeringBehavior<Vector2> getBehavior()
    {
        return behavior;
    }






    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
       this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {

    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return (Location<Vector2>) new Vector2();
    }


}

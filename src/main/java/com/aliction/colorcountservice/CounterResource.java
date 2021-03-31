package com.aliction.colorcountservice;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.uni.builders.UniCreateFromKnownItem;

@Path("/count")
public class CounterResource {
    Logger logger = LoggerFactory.getLogger(CounterResource.class);

    Long orangeCount = (long) 0;
    Long greenCount = (long) 0;
    Long redCount = (long) 0;
    Long blueCount = (long) 0;
    Long purpleCount = (long) 0;

    
    @Inject
    io.vertx.mutiny.pgclient.PgPool client;


    @POST
    @Path("/{color}")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> count(ColorObject colorObj, @PathParam("color") String color) {
        switch(color){
            case "orange": IncrementOrange(); break;
            case "green": IncrementGreen(); break;
            case "red": IncrementRed(); break;
            case "blue": IncrementBlue(); break;
            case "purple": IncrementPurple(); break;
        }
        // colorEmitter.send(colorObj);
        Uni<String> rowId = colorObj.save(client)
        .onItem().transform(id -> {logger.info(id.toString() + "Saved"); return id.toString();});
        return rowId;
    }

    @Counted
    public Long IncrementOrange(){
        return orangeCount++;
    }

    @Counted
    public Long IncrementGreen(){
        return greenCount++;
    }

    @Counted
    public Long IncrementRed(){
        return redCount++;
    }

    @Counted
    public Long IncrementBlue(){
        return blueCount++;
    }

    @Counted
    public Long IncrementPurple(){
        return purpleCount++;
    }
}
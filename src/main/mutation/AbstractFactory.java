package main.mutation;

/**
 * Created by Pawel_Piedel on 19.10.2017.
 */
public abstract class AbstractFactory {
    abstract Mutation createMutation(MutationType mutationType);
}

package main.mutation;

/**
 * Created by Pawel_Piedel on 19.10.2017.
 */
public class MutationFactory extends AbstractFactory {

    @Override
    public Mutation createMutation(MutationType mutationType) {
        Mutation mutation = null;
        switch (mutationType) {
            case SCRAMBLE_MUTATION:
                mutation = new ScrambleMutation();
                break;
            default:
                //ignore
        }
        return mutation;
    }
}

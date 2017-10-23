package main.mutation;

/**
 * Created by Pawel_Piedel on 19.10.2017.
 */
public class MutationFactory{

    public static Mutation createMutation(MutationType mutationType) {
        Mutation mutation = null;
        switch (mutationType) {
            case SCRAMBLE_MUTATION:
                mutation = new ScrambleMutation();
                break;
            case INVERSE_MUTATION:
                mutation = new InverseMutation();
                break;
            default:
                //ignore
        }
        return mutation;
    }
}

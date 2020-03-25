package pl.polsl.task.manager.rest.api.exceptions;

@SuppressWarnings("rawtypes")
public class ForbiddenAccessException extends RuntimeException {

    public ForbiddenAccessException(String message){
        super(message);
    }

    public ForbiddenAccessException(Class...users){
        super(produceMessage(users));
    }

    private static String produceMessage(Class...users){
        StringBuilder message = new StringBuilder("This resource is available only for: ");
        for (Class user : users) {
            message.append(user.getSimpleName()).append(", ");
        }
        return message.toString();
    }

}

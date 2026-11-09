package se.fulkoping.uthyrning.exception;

public record FelSvar (
        int status,
        String fel
){
}

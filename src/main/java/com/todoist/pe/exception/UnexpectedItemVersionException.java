package com.todoist.pe.exception;

public class UnexpectedItemVersionException extends NotFoundException {
    public UnexpectedItemVersionException(Long expectedVersion, Long foundVersion) {
        super(String.format("The item has a different version than the expected one. Expected [%s], found [%s]",
                expectedVersion, foundVersion));
    }
}

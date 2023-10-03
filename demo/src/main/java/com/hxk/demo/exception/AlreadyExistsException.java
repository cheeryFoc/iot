package com.hxk.demo.exception;

import com.hxk.demo.constant.ErrorConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * AlreadyExistsException
 *
 * @author cheer
 */
public class AlreadyExistsException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 4775907845387588528L;

    public AlreadyExistsException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.CONFLICT);
    }
}

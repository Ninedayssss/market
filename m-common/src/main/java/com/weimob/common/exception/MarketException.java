package com.weimob.common.exception;

import com.weimob.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author itsNine
 * @create 2020-03-17-15:00
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MarketException extends RuntimeException{

    private ExceptionEnum exceptionEnum;
}

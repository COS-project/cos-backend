package com.cos.cercat.apis.global.resolver;

import com.cos.cercat.common.annotation.CursorDefault;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SortDirection;
import com.cos.cercat.common.domain.SortOrder;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.List;

public class CursorArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Cursor.class)
                && parameter.hasParameterAnnotation(CursorDefault.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        CursorDefault cursorDefault = parameter.getParameterAnnotation(CursorDefault.class);

        String pageParam = webRequest.getParameter("page");
        int page = (pageParam != null) ? Integer.parseInt(pageParam) : cursorDefault.page();

        String sizeParam = webRequest.getParameter("size");
        int size = (sizeParam != null) ? Integer.parseInt(sizeParam) : cursorDefault.size();

        List<SortOrder> sortOrders = new ArrayList<>();
        String sortFieldsParam = webRequest.getParameter("sortFields");
        String sortDirectionsParam = webRequest.getParameter("sortDirections");

        String[] fields = (sortFieldsParam != null) ? sortFieldsParam.split(",") : cursorDefault.sortFields().split(",");
        String[] directions = (sortDirectionsParam != null) ? sortDirectionsParam.split(",") : cursorDefault.sortDirections().split(",");

        // 필드와 방향 배열의 길이가 일치하지 않는 경우, 짧은 배열의 길이에 맞춰 정렬
        int length = Math.min(fields.length, directions.length);
        for (int i = 0; i < length; i++) {
            SortDirection direction = SortDirection.valueOf(directions[i].trim().toUpperCase());
            sortOrders.add(new SortOrder(fields[i].trim(), direction));
        }

        return new Cursor(page, size, sortOrders);
    }
}

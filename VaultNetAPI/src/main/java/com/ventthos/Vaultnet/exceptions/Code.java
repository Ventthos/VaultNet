package com.ventthos.Vaultnet.exceptions;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Code {
    INVALID_CREDENTIALS("Correo o contraseña incorrectos", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("El usuario no se encuentra registrado", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD("La contraseña debe tener al menos 6 caracteres", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_REGISTERED("Un usuario con ese email ya se encuentra registrado", HttpStatus.BAD_REQUEST),
    CAN_NOT_SAVE("No se pudo guardar el elemento", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_SAVED("El usuario se registró correctamente", HttpStatus.CREATED),
    ELEMENT_GET_SUCCESSFUL("El elemento se obtuvo correctamente", HttpStatus.OK),
    ACCESS_DENIED("No tienes permiso para acceder a este recurso", HttpStatus.FORBIDDEN),
    VALIDATION_ERROR("Error de validación de datos, verifique los campos obligatorios", HttpStatus.BAD_REQUEST),
    BUSINESS_NOT_FOUND("No se encontró el negocio", HttpStatus.NOT_FOUND),
    UNIT_NOT_FOUND("No se encontró la unidad en el negocio proporcionado", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND("No se encontró la categoria en el negocio proporcionado", HttpStatus.NOT_FOUND),
    INTERNAL_ERROR("Ocurrió un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_CREATED("Categoría creada", HttpStatus.CREATED),
    UNIT_CREATED("Unidad creada", HttpStatus.CREATED),
    BUSINESS_CREATED("Negocio creado", HttpStatus.CREATED),
    LOGGED_IN("Sesión iniciada correctamente", HttpStatus.OK),
    ROUTE_NOT_FOUND("La ruta solicitada no existe: ", HttpStatus.NOT_FOUND),
    PRODUCT_CREATED("Producto creado exitosamente", HttpStatus.CREATED),
    PRODUCT_NOT_FOUND("No se encontró el producto", HttpStatus.NOT_FOUND),
    FILE_TOO_BIG("Se excedió el tamaño máximo para imagenes max: 10 MB", HttpStatus.BAD_REQUEST),
    PRODUCT_EDITED("El producto se editó correctamente", HttpStatus.OK),
    METHOD_NOT_ALLOWED("El método solicitado no está disponible", HttpStatus.METHOD_NOT_ALLOWED),
    USERS_ADDED("Usuarios agregados al negocio", HttpStatus.OK),
    MISSING_PARAMETER("Faltaron algunos parámetros en la solicitud", HttpStatus.BAD_REQUEST);

    private final String defaultMessage;
    private final HttpStatus httpStatus;

    Code(String defaultMessage, HttpStatus httpStatus) {
        this.defaultMessage = defaultMessage;
        this.httpStatus = httpStatus;
    }

}

package com.sistemalima.pickPayPlataform.adapters.controller.validated

import jakarta.validation.Constraint
import org.hibernate.validator.constraints.CompositionType
import org.hibernate.validator.constraints.ConstraintComposition
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import kotlin.reflect.KClass


@MustBeDocumented     // esta validação vai aparecer na documentação da classe que utiliza-la
@Constraint(validatedBy = [])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@CPF
@CNPJ
@ConstraintComposition(CompositionType.OR)
annotation class CpfOrCnpj(
    val message: String = "Invalid CPF or CNPJ document",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Any>> = []
)

package com.todolist.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTodoList is a Querydsl query type for TodoList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTodoList extends EntityPathBase<TodoList> {

    private static final long serialVersionUID = 1596952466L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTodoList todoList = new QTodoList("todoList");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final BooleanPath complete = createBoolean("complete");

    public final StringPath content = createString("content");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final DateTimePath<java.time.LocalDateTime> deadline = createDateTime("deadline", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final EnumPath<com.todolist.constant.Status> status = createEnum("status", com.todolist.constant.Status.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QTodoList(String variable) {
        this(TodoList.class, forVariable(variable), INITS);
    }

    public QTodoList(Path<? extends TodoList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTodoList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTodoList(PathMetadata metadata, PathInits inits) {
        this(TodoList.class, metadata, inits);
    }

    public QTodoList(Class<? extends TodoList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}


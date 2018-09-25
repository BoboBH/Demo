
from sqlalchemy import Column,String,Integer,DateTime, Numeric,create_engine,ForeignKey;
from sqlalchemy.orm import sessionmaker;
def toDict(alchemyObject):
    print("try to convert object to dict...");
    for col in alchemyObject.__table__.columns:
        if isinstance(col.type, DateTime):
            value = convert_datetime(value);
        elif isinstance(col.type, Numeric):
            value = float(getattr(alchemyObject, col.name));
        else:
            value = getattr(alchemyObject, col.name);
        yield (col.name, value);

def gen_dict(alchemyObject):
    dict = {};
    for col in alchemyObject.__table__.columns:
        if isinstance(col.type, DateTime):
            value = convert_datetime(value);
        elif isinstance(col.type, Numeric):
            value = float(getattr(alchemyObject, col.name));
        else:
            value = getattr(alchemyObject, col.name);
        dict[col.name] = value;
    return dict;
def to_dict(alchemyObject):
    return {c.name: getattr(alchemyObject, c.name, None) for c in alchemyObject.__table__.columns};
class Config(object):
    pass

class ProdConfig(Config):
    DEBUG = False
    PORT = 9002

class DevConfig(Config):
    DEBUG = True
    PORT = 9002
    #SERVER_NAME = ":9002"
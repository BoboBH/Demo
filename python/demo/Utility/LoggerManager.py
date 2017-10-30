
import logging
import logging.config

class LoggerManager:

    @staticmethod #静态方法
    def getLogger(typeName):
       logging.config.fileConfig("logger.conf");
       return logging.getLogger(typeName);
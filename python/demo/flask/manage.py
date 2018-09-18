from flask_script import Manager, Server
from main import myapp

manager = Manager(myapp)
manager.add_command("server", Server())
@manager.shell
def make_shell_context():
    return dict(app=myapp)
if __name__ == "__main__":
    manager.run();
using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(Task_02.Startup))]
namespace Task_02
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}

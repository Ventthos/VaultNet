
using Microsoft.AspNetCore.Authentication;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Hosting;

namespace VaultNet.Db
{
    public class VaultNetContext: DbContext
    {
        public VaultNetContext(DbContextOptions<VaultNetContext> options) : base(options)
        {

        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<User>()
                .HasIndex(u => u.Email)
                .IsUnique();
            
            modelBuilder.Entity<Category>()
                .HasIndex(c => new {c.BussinessId,c.Name})
                .IsUnique();

            modelBuilder.Entity<Unit>()
                .HasIndex(u => new { u.BussinessId, u.Name })
                .IsUnique();

            modelBuilder.Entity<UserBussiness>()
                .HasKey(ub => new { ub.UserId, ub.BussinessId });

            modelBuilder.Entity<UserBussiness>()
                .HasOne(ub => ub.User)
                .WithMany(u => u.Bussiness)
                .HasForeignKey(ub => ub.UserId);

            modelBuilder.Entity<UserBussiness>()
                .HasOne(ub => ub.Bussiness)
                .WithMany(b => b.Users)
                .HasForeignKey(ub => ub.BussinessId);
        }

        public DbSet<User> Users { get; set; }
        public DbSet<Bussiness> Bussiness { get; set; }
        public DbSet<Product> Products { get; set; }
        public DbSet<Category> Categories { get; set; }
        public DbSet<Unit> Units { get; set; }
        public DbSet<Snapshot> Snapshots { get; set; }
    }
}

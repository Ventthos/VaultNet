using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using VaultNet.Db;

namespace ChargedChat.Db
{
    public class Bussiness
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int BussinessId { get; set; }

        [Required]
        [StringLength(100)]
        public required string Name { get; set; }

        [Required]
        [StringLength(500)]
        public string? LogoUrl { get; set; }

        public virtual ICollection<User> Users { get; set; }

        public virtual ICollection<Product> Products { get; set; }

        
    }
}

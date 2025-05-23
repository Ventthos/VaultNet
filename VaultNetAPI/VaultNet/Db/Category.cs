﻿using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace VaultNet.Db
{
    public class Category
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int CategoryId { get; set; }
        
        public int BussinessId { get; set; }

        [ForeignKey(nameof(BussinessId))]
        public virtual required Bussiness Bussiness { get; set; }

        [Required]
        [StringLength(100)]
        public required string Name { get; set; }

        [StringLength(7)]
        public required string Color { get; set; }

        public virtual ICollection<Product> Products { get; set; } = new HashSet<Product>();
    }
}
